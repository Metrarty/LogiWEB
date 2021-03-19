INSERT INTO CITY (city_name, created_at) VALUES
  ('Kiev', now()),
  ('Moscow', now()),
  ('Ufa', now()),
  ('Omsk', now());

INSERT INTO DISTANCE (city1_id, city2_id, distance) VALUES
('1', '2', '857'),
('1', '3', '2032'),
('1', '4', '3261'),
('2', '3', '1355'),
('2', '4', '2743'),
('3', '4', '1346');

INSERT INTO TRUCK (capacity, location_id, distance_per_day) VALUES
('300', '2', '1000'),
('700', '4', '800'),
('1000', '2', '1500');

INSERT INTO CARGO (size, created_at) VALUES
('1130', now()),
('570', now());

INSERT INTO ORDERS (cargo_id, destination_id, delivery_date, created_at) VALUES
('1', '1', '2021-05-17', now());
