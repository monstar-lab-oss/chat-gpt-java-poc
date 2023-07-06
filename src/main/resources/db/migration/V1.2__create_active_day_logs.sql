CREATE TABLE active_day_logs
(
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT NOT NULL,
    weight               DOUBLE PRECISION,
    active_calories      INTEGER,
    active_calories_goal INTEGER,
    date                 DATE,
    active_day           BOOLEAN,
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP,
    CONSTRAINT fk_users
      FOREIGN KEY(user_id)
	  REFERENCES users(id)
	  ON DELETE CASCADE,
	CONSTRAINT activedaylogs_userid_date_key UNIQUE (user_id, date)
);
