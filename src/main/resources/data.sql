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
('700', '4', '800', 'ASSIGNED'),
('1000', '2', '1500', 'BROKEN');

INSERT INTO CARGO (size, created_at) VALUES
('1130', now()),
('570', now());

INSERT INTO ORDERS (cargo_id, destination_id, approximately_delivery_date, created_at, assigned_truck_id) VALUES
('1', '1', '2021-05-17', now(), '1'),
('2', '4', '2021-06-20', now(), '2');
