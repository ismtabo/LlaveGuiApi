CREATE TABLE users (
    "nick" Text NOT NULL UNIQUE NOT NULL,
    "passwd" Text NOT NULL NOT NULL
);