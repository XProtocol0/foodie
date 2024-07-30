CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_account(
  id SERIAL not null PRIMARY KEY,
  name varchar(50),
  password varchar(1024),
  email varchar(1024),
  created_on TIMESTAMP WITHOUT TIME ZONE,
  updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS shipper(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    user_id BIGINT REFERENCES user_account UNIQUE,
    rating NUMERIC,
    available BOOLEAN,
    location Geometry(Point, 4326)
);

CREATE TABLE IF NOT EXISTS customer(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    user_id BIGINT REFERENCES user_account UNIQUE,
    rating NUMERIC
);

CREATE TABLE IF NOT EXISTS order_request(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    restaurant_location Geometry(Point, 4326),
    delivery_location Geometry(Point, 4326),
    customer_id UUID REFERENCES customer,
    restaurant_id UUID,
    items JSONB,
    payment_type VARCHAR(30),
    order_request_status VARCHAR(30),
    created_on TIMESTAMP WITHOUT TIME ZONE,
    updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS orders(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    order_request_id UUID REFERENCES order_request,
    shipper_id UUID REFERENCES shipper,
    order_status VARCHAR(30),
    created_on TIMESTAMP WITHOUT TIME ZONE,
    updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS payment(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    payment_method VARCHAR(30),
    order_id UUID REFERENCES orders,
    amount NUMERIC,
    payment_status VARCHAR(30),
    payment_time TIMESTAMP WITHOUT TIME ZONE,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS wallet(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    user_id BIGINT REFERENCES user_account UNIQUE,
    amount NUMERIC,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS wallet_transaction(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    amount NUMERIC,
    transaction_type VARCHAR(30),
    transaction_method VARCHAR(30),
    order_id UUID REFERENCES orders,
    wallet_id UUID REFERENCES wallet,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    updated_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS restaurant(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    user_id BIGINT REFERENCES user_account UNIQUE,
    address VARCHAR(200),
    zip_code VARCHAR(10),
    opening_hour INTEGER,
    closing_hour INTEGER,
    rating NUMERIC,
    location Geometry(Point, 4326)
);

CREATE TABLE IF NOT EXISTS menu(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    restaurant_id UUID REFERENCES restaurant,
    item_name VARCHAR(50),
    price NUMERIC,
    vegetarian BOOLEAN,
    ingredients VARCHAR(200)
);