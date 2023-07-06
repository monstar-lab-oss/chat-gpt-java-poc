CREATE TABLE workouts
(
    id                                   BIGSERIAL PRIMARY KEY,
    user_id                              BIGINT NOT NULL,
    workout_type                         VARCHAR(100) NOT NULL,
    start_date                           TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date                             TIMESTAMP WITH TIME ZONE NOT NULL,
    total_distance_in_metres             INT,
    total_flights_climbed                INT,
    total_swimming_stroke_count          INT,
    total_energy_burned_in_kilo_calories INT,
    created_at                           TIMESTAMP,
    updated_at                           TIMESTAMP,
    CONSTRAINT fk_user
      FOREIGN KEY(user_id)
	  REFERENCES users(id) ON DELETE CASCADE
);