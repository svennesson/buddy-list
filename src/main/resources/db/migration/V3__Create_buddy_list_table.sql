CREATE TABLE buddy_list (
    id SERIAL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    PRIMARY KEY(id)
);

CREATE TABLE users_has_buddy_lists (
    buddy_list_id integer NOT NULL,
    user_id integer NOT NULL,
    PRIMARY KEY(buddy_list_id, user_id),
    CONSTRAINT fk_userLists FOREIGN KEY (buddy_list_id)
    REFERENCES buddy_list(id),
    CONSTRAINT fkListUsers FOREIGN KEY (user_id)
    REFERENCES users(id)
);