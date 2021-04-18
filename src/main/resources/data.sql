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

INSERT INTO TRUCK (capacity, location_id, distance_per_day, truck_status) VALUES
('300', '2', '1000', 'FREE'),
('700', '4', '800', 'FREE'),
('1000', '2', '1500', 'FREE');

INSERT INTO CARGO (size, created_at) VALUES
('1130', now()),
('570', now()),
('666', now()),
('999', now());

INSERT INTO ORDERS (cargo_id, source_city_id, destination_id, order_status, created_at) VALUES
('1', '1', '2', 'CREATED', now()),
('2', '4', '3', 'CREATED', now());
