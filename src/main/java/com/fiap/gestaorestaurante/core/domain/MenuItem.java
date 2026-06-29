package com.fiap.gestaorestaurante.core.domain;

import java.math.BigDecimal;

public class MenuItem {
    private Long id;
    private Restaurant restaurant;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean dineInOnly;
    private String photoPath;

    public MenuItem() {
    }

    public MenuItem(Restaurant restaurant, String name, String description, BigDecimal price,
                    boolean dineInOnly, String photoPath) {
        this(null, restaurant, name, description, price, dineInOnly, photoPath);
    }

    public MenuItem(Long id, Restaurant restaurant, String name, String description, BigDecimal price,
                    boolean dineInOnly, String photoPath) {
        this.id = id;
        update(restaurant, name, description, price, dineInOnly, photoPath);
    }

    public void update(Restaurant restaurant, String name, String description, BigDecimal price,
                       boolean dineInOnly, String photoPath) {
        this.restaurant = restaurant;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dineInOnly = dineInOnly;
        this.photoPath = photoPath;
    }

    public Long getId() { return id; }
    public Restaurant getRestaurant() { return restaurant; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public boolean isDineInOnly() { return dineInOnly; }
    public String getPhotoPath() { return photoPath; }
}
