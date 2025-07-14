CREATE TABLE IF NOT EXISTS currency (
    code VARCHAR(255) NOT NULL,
    quote VARCHAR(255),
    rate VARCHAR(255),
    date VARCHAR(255),
    name VARCHAR(255),
    PRIMARY KEY (code)
);