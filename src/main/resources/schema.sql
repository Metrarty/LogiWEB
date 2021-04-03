DROP TABLE IF EXISTS CITY;
DROP TABLE IF EXISTS DISTANCE;
DROP TABLE IF EXISTS TRUCK;
DROP TABLE IF EXISTS CARGO;
DROP TABLE IF EXISTS ORDERS;

CREATE TABLE CITY (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
  city_name VARCHAR(250),
  created_at TIMESTAMP NOT NULL,
  changed_at TIMESTAMP
);

CREATE TABLE DISTANCE (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 city1_id BIGINT,
 city2_id BIGINT,
 distance BIGINT
);

CREATE TABLE TRUCK (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
capacity BIGINT,
location_id BIGINT,
distance_per_day BIGINT
);

CREATE TABLE CARGO (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
  size BIGINT,
  created_at TIMESTAMP NOT NULL,
  changed_at TIMESTAMP
);

CREATE TABLE ORDERS (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cargo_id BIGINT,
  destination_id BIGINT,
  approximately_delivery_date DATE,
  created_at TIMESTAMP NOT NULL,
  changed_at TIMESTAMP,
  delivered_at TIMESTAMP,
  completed_at TIMESTAMP,
  assigned_truck_id BIGINT
);



