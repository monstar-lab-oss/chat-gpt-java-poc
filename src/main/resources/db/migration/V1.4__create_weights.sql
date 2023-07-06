CREATE TABLE app_user_weight
(
    id BIGSERIAL PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    weight_in_pounds DOUBLE PRECISION,
    processed BOOLEAN DEFAULT false,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) on delete cascade
);