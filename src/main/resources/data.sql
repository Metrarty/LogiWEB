INSERT INTO CITY (city_name, created_at) VALUES
  ('Omsk', now()),
  ('Tomsk', now()),
  ('Ufa', now());

INSERT INTO DISTANCE (city1_id, city2_id, distance) VALUES
('1', '2', '345'),
('2', '3', '564');

INSERT INTO TRUCK (capacity, location_id, distance_per_day) VALUES
('500', '2', '1000'),
('700', '3', '800');

INSERT INTO CARGO (size, created_at) VALUES
('1130', now()),
('570', now());