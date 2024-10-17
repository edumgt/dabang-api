CREATE DATABASE IF NOT EXISTS dabang;
USE dabang;


-- users
drop table if exists users;
create table users (
                       id bigint not null auto_increment,
                       email varchar(255) not null,
                       password varchar(255) not null,
                       primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- init password : password123
INSERT INTO USERS (email, password)
VALUES ('test@test.com', '$2a$10$3fhB/6xjWn3jDgFnFBC0puMABEyUHLVqIdhupPUCRBZJ6OUou34n6');

-- room
drop table if exists room;
create table ROOM (
                       id bigint not null auto_increment,
                       user_id bigint not null,
                       room_type varchar(255) not null,
                       selling_type varchar(255) not null,
                       deposit bigint,
                       price bigint,
                       description varchar(255),
                       primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE INDEX idx_user_room_filters
    ON ROOM (user_id, room_type, selling_type, deposit, price);

CREATE INDEX idx_room_filters
    ON ROOM (room_type, selling_type, deposit, price);

INSERT INTO ROOM (user_id, room_type, selling_type, deposit, price, description) VALUES
                        (1, "ONE_ROOM", "MONTHLY_RENT", 10000000, 500000, "원룸1000에50입니다."),
                        (1, "ONE_ROOM", "MONTHLY_RENT", 20000000, 300000, "원룸2000에30야~"),
                        (1, "TWO_ROOM", "LEASE", 50000000, 0, "투룸5000전세!!"),
                        (1, "THREE_ROOM", "LEASE", 70000000, 0, "쓰리룸7000전세@@@");
