package com.fiap.gestaorestaurante.core.domain;

public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private User owner;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String cuisineType, String openingHours, User owner) {
        this(null, name, address, cuisineType, openingHours, owner);
    }

    public Restaurant(Long id, String name, String address, String cuisineType, String openingHours, User owner) {
        this.id = id;
        update(name, address, cuisineType, openingHours, owner);
    }

    public void update(String name, String address, String cuisineType, String openingHours, User owner) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCuisineType() { return cuisineType; }
    public String getOpeningHours() { return openingHours; }
    public User getOwner() { return owner; }
}
