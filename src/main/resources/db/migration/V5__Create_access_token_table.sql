CREATE TABLE access_token (
    id SERIAL,
    user_id INTEGER UNIQUE,
    token UUID UNIQUE,
    date_issued TIMESTAMPTZ,
    PRIMARY KEY(id)
)