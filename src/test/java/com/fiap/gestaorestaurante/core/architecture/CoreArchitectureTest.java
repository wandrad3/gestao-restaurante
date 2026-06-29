package com.fiap.gestaorestaurante.core.architecture;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoreArchitectureTest {

    private static final List<String> FORBIDDEN_IMPORTS = List.of(
            "import com.fiap.gestaorestaurante.infra.",
            "import com.fiap.gestaorestaurante.infrastructure.",
            "import com.fiap.gestaorestaurante.application.",
            "import com.fiap.gestaorestaurante.domain.model.",
            "import jakarta.persistence.",
            "import javax.persistence.",
            "import org.springframework."
    );

    private static final List<String> FORBIDDEN_ANNOTATIONS = List.of(
            "@Entity",
            "@Table",
            "@RestController",
            "@Controller",
            "@Service",
            "@Repository",
            "@Component"
    );

    @Test
    void coreShouldNotDependOnFrameworksOrOuterLayers() throws IOException {
        Path corePath = Path.of("src/main/java/com/fiap/gestaorestaurante/core");

        List<String> violations = Files.walk(corePath)
                .filter(path -> path.toString().endsWith(".java"))
                .flatMap(path -> violationsIn(path).stream())
                .toList();

        assertThat(violations).isEmpty();
    }

    private List<String> violationsIn(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .filter(this::isForbiddenLine)
                    .map(line -> path + ": " + line.strip())
                    .toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Could not inspect " + path, exception);
        }
    }

    private boolean isForbiddenLine(String line) {
        String stripped = line.stripLeading();
        return FORBIDDEN_IMPORTS.stream().anyMatch(stripped::startsWith)
                || FORBIDDEN_ANNOTATIONS.stream().anyMatch(stripped::startsWith);
    }
}
