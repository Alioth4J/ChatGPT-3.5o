create database `chatgpt-3p5o`;
use `chatgpt-3p5o`;

create table `user` (
                        id bigint auto_increment primary key,
                        username varchar(255) not null unique,
                        password varchar(255) not null,
                        phone varchar(20),
                        icon varchar(255),
                        create_time datetime not null,
                        point int not null default 0
);

CREATE TABLE `history` (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `user_id` BIGINT NOT NULL,
                           `question` VARCHAR(255),
                           `answer` VARCHAR(255),
                           `create_time` DATETIME NOT NULL
);