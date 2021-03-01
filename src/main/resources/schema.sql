DROP TABLE IF EXISTS CITY;
DROP TABLE IF EXISTS DISTANCE;

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

