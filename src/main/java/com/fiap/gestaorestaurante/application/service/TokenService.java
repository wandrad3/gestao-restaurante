package com.fiap.gestaorestaurante.application.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.gestaorestaurante.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    private final String secret;
    private final long expirationMillis;

    public TokenService(@Value("${jwt.secret:default-secret-change-this}") String secret,
                        @Value("${jwt.expiration:3600000}") long expirationMillis) {
        this.secret = secret == null ? "default-secret-change-this" : secret;
        this.expirationMillis = expirationMillis;
    }

    public String createToken(User user) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiresAt = Date.from(now.plusMillis(expirationMillis));

        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt);

        Optional<Object> idOpt = safeInvoke(user, "getId");
        idOpt.ifPresent(id -> builder.withSubject(String.valueOf(id)));

        safeInvoke(user, "getEmail").ifPresent(e -> builder.withClaim("email", String.valueOf(e)));

        // Evitar lazy loading - não extrair roles aqui
        try {
            List<String> roles = extractRoles(user);
            if (!roles.isEmpty()) {
                builder.withArrayClaim("roles", roles.toArray(new String[0]));
            }
        } catch (Exception e) {
            log.debug("Não foi possível extrair roles: {}", e.getMessage());
        }

        try {
            extractUserType(user).ifPresent(val -> {
                if (val instanceof Number) {
                    builder.withClaim("userType", ((Number) val).longValue());
                } else {
                    builder.withClaim("userType", String.valueOf(val));
                }
            });
        } catch (Exception e) {
            log.debug("Não foi possível extrair userType: {}", e.getMessage());
        }

        idOpt.ifPresent(id -> {
            if (id instanceof Number) builder.withClaim("id", ((Number) id).longValue());
            else builder.withClaim("id", String.valueOf(id));
        });

        return builder.sign(algorithm);
    }

    public User getUsuarioFromToken(String token) {
        if (token == null || token.isBlank()) return null;
        String raw = token.trim();
        if (raw.startsWith("Bearer ")) raw = raw.substring(7).trim();

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
            DecodedJWT jwt = JWT.require(algorithm).build().verify(raw);

            String idStr = null;
            if (!jwt.getClaim("id").isNull()) {
                try {
                    Long l = jwt.getClaim("id").asLong();
                    if (l != null) idStr = String.valueOf(l);
                } catch (Exception ex) {
                    idStr = jwt.getClaim("id").asString();
                }
            }
            if (idStr == null && jwt.getSubject() != null && !jwt.getSubject().isBlank()) {
                idStr = jwt.getSubject();
            }

            String email = null;
            try {
                email = jwt.getClaim("email").asString();
            } catch (Exception ignored) { }

            String[] roles = null;
            try {
                if (!jwt.getClaim("roles").isNull()) roles = jwt.getClaim("roles").asArray(String.class);
            } catch (Exception ignored) { }

            User user = instantiateUser();
            if (user == null) return null;

            if (idStr != null) setProperty(user, "id", idStr);
            if (email != null) setProperty(user, "email", email);
            if (roles != null) setProperty(user, "roles", Arrays.asList(roles));

            return user;
        } catch (Exception e) {
            log.debug("Token inválido ou erro ao decodificar: {}", e.getMessage());
            return null;
        }
    }

    private User instantiateUser() {
        try {
            var constructor = User.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            log.debug("Falha ao instanciar User por reflexão: {}", e.getMessage());
            return null;
        }
    }

    private void setProperty(Object target, String propName, Object value) {
        if (target == null || value == null) return;
        Class<?> cls = target.getClass();
        String setter = "set" + Character.toUpperCase(propName.charAt(0)) + propName.substring(1);

        try {
            try {
                Method m = cls.getMethod(setter, String.class);
                m.invoke(target, String.valueOf(value));
                return;
            } catch (NoSuchMethodException ignored) { }

            try {
                Method m = cls.getMethod(setter, Long.class);
                if (value instanceof Number) m.invoke(target, ((Number) value).longValue());
                else m.invoke(target, Long.valueOf(String.valueOf(value)));
                return;
            } catch (NoSuchMethodException ignored) { }

            try {
                Method m = cls.getMethod(setter, long.class);
                if (value instanceof Number) m.invoke(target, ((Number) value).longValue());
                else m.invoke(target, Long.parseLong(String.valueOf(value)));
                return;
            } catch (NoSuchMethodException ignored) { }

            try {
                Method m = cls.getMethod(setter, Collection.class);
                if (value instanceof Collection) m.invoke(target, value);
                return;
            } catch (NoSuchMethodException ignored) { }

            try {
                Field f = cls.getDeclaredField(propName);
                f.setAccessible(true);
                if ((f.getType() == Long.class || f.getType() == long.class) && !(value instanceof Number)) {
                    f.set(target, Long.parseLong(String.valueOf(value)));
                } else if ((f.getType() == Long.class || f.getType() == long.class) && value instanceof Number) {
                    f.set(target, ((Number) value).longValue());
                } else if (f.getType() == String.class) {
                    f.set(target, String.valueOf(value));
                } else if (Collection.class.isAssignableFrom(f.getType()) && value instanceof Collection) {
                    f.set(target, value);
                } else {
                    f.set(target, value);
                }} catch (NoSuchFieldException ignored) { }
        } catch (Exception e) {
            log.debug("Erro ao setar propriedade {}: {}", propName, e.getMessage());
        }
    }

    private List<String> extractRoles(User user) {
        try {
            if (user instanceof UserDetails) {
                UserDetails ud = (UserDetails) user;
                return ud.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.debug("Erro ao extrair authorities: {}", e.getMessage());
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }


    private Optional<Object> extractUserType(User user) {
        String[] methodNames = {
                "getUserType", "getUser_type", "getUserTypeId",
                "getUserTypeName", "getTipoUsuario", "getTipoUsuarioId", "getTipoUsuarioNome"
        };
        for (String m : methodNames) {
            Optional<Object> v = safeInvoke(user, m);
            if (v.isPresent()) {
                Object val = v.get();
                Optional<Object> nested = tryGetNestedNameOrId(val);
                return nested.or(() -> Optional.ofNullable(val));
            }
        }
        String[] fieldNames = {"userType", "user_type", "userTypeId", "tipoUsuario", "tipo_usuario"};
        for (String f : fieldNames) {
            Optional<Object> v = safeGetField(user, f);
            if (v.isPresent()) {
                Object val = v.get();
                Optional<Object> nested = tryGetNestedNameOrId(val);
                return nested.or(() -> Optional.ofNullable(val));
            }
        }
        return Optional.empty();
    }

    private Optional<Object> tryGetNestedNameOrId(Object obj) {
        if (obj == null) return Optional.empty();
        String[] nested = {"getName", "getNome", "getId", "getIdUser", "getUserTypeId"};
        for (String m : nested) {
            try {
                Method method = obj.getClass().getMethod(m);
                Object val = method.invoke(obj);
                if (val != null) return Optional.of(val);
            } catch (Exception ignored) { }
        }
        return Optional.empty();
    }

    private Optional<Object> safeInvoke(Object target, String methodName) {
        if (target == null) return Optional.empty();
        try {
            Method m = target.getClass().getMethod(methodName);
            Object val = m.invoke(target);
            return Optional.ofNullable(val);
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        } catch (Exception e) {
            log.debug("Erro ao invocar método {} em {}: {}", methodName, target.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Object> safeGetField(Object target, String fieldName) {
        if (target == null) return Optional.empty();
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return Optional.ofNullable(f.get(target));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        } catch (Exception e) {
            log.debug("Erro ao ler campo {} em {}: {}", fieldName, target.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }
}
