package com.fiap.gestaorestaurante.core.domain;

import java.time.OffsetDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private UserType userType;
    private OffsetDateTime updatedAt;

    public User() {
    }

    public User(String name, String email, String username, String password, String street,
                String number, String city, String state, String zipCode, UserType userType) {
        this(null, name, email, username, password, street, number, city, state, zipCode, userType, null);
    }

    public User(Long id, String name, String email, String username, String password, String street,
                String number, String city, String state, String zipCode, UserType userType,
                OffsetDateTime updatedAt) {
        this.id = id;
        this.password = password;
        this.updatedAt = updatedAt;
        update(name, email, username, street, number, city, state, zipCode, userType);
    }

    public void update(String name, String email, String username, String street,
                       String number, String city, String state, String zipCode, UserType userType) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.userType = userType;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
    public UserType getUserType() { return userType; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
