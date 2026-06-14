CREATE TABLE user_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    CONSTRAINT uk_user_types_name UNIQUE (name)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(180) NOT NULL,
    username VARCHAR(80) NOT NULL,
    password VARCHAR(255) NOT NULL,
    street VARCHAR(120) NOT NULL,
    number VARCHAR(20) NOT NULL,
    city VARCHAR(80) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    user_type_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT fk_users_user_type FOREIGN KEY (user_type_id) REFERENCES user_types (id)
);

CREATE INDEX idx_users_lower_name ON users (LOWER(name));

CREATE TABLE restaurants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    cuisine_type VARCHAR(100) NOT NULL,
    opening_hours VARCHAR(120) NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_restaurants_owner FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE menu_items (
    id BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price NUMERIC(12, 2) NOT NULL CHECK (price >= 0),
    dine_in_only BOOLEAN NOT NULL,
    photo_path VARCHAR(500) NOT NULL,
    CONSTRAINT fk_menu_items_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);
