CREATE TABLE users(
id bigserial PRIMARY KEY, 
first_name VARCHAR(100), 
last_name VARCHAR(100),
weight_in_pounds double precision DEFAULT 0,
active_calories_goal integer DEFAULT 0,
created_at TIMESTAMP,
updated_at TIMESTAMP);
