package com.fiap.gestaorestaurante.core.domain;

public class UserType {
    private Long id;
    private String name;

    public UserType() {
    }

    public UserType(String name) {
        this(null, name);
    }

    public UserType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
