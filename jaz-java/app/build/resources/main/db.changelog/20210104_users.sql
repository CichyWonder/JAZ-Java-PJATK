CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR,
    password VARCHAR,
    role VARCHAR
)
