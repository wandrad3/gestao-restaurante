package com.fiap.gestaorestaurante.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean dineInOnly;

    @Column(nullable = false, length = 500)
    private String photoPath;

    protected MenuItem() {
    }

    public MenuItem(Restaurant restaurant, String name, String description, BigDecimal price,
                    boolean dineInOnly, String photoPath) {
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
