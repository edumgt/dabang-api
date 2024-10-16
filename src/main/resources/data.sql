CREATE DATABASE IF NOT EXISTS dabang;
USE dabang;

drop table room;
create table ROOM (
                       id bigint not null auto_increment,
                       name varchar(255),
                       room_type varchar(255) not null,
                       selling_type varchar(255) not null,
                       deposit bigint,
                       price bigint,
                       primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE INDEX idx_room_type_selling_type
    ON ROOM (room_type, selling_type);
CREATE INDEX idx_room_filters
    ON ROOM (room_type, selling_type, deposit, price);

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ROOM;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO ROOM (name, room_type, selling_type, deposit, price) VALUES
                        ("원룸1000에50", "ONE_ROOM", "MONTHLY_RENT", 10000000, 500000),
                        ("원룸2000에30", "ONE_ROOM", "MONTHLY_RENT", 20000000, 300000),
                        ("투룸5000전세", "TWO_ROOM", "LEASE", 50000000, 0),
                        ("쓰리룸7000전세", "THREE_ROOM", "LEASE", 70000000, 0)
;