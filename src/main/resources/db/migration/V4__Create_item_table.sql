CREATE TABLE item (
    id SERIAL,
    text VARCHAR(100) NOT NULL,
    checked boolean NOT NULL,
    store VARCHAR(40),
    quantity INTEGER,
    price INTEGER,
    buddy_list_id INTEGER,
    PRIMARY KEY(id),
    CONSTRAINT fk_listItem FOREIGN KEY (buddy_list_id)
    REFERENCES buddy_list(id)
)