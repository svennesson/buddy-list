CREATE TABLE users (
    id SERIAL,
    name varchar(50) not null,
    age integer not null,
    email varchar(70) not null,
    password varchar(50) not null,
    primary key(id)
)