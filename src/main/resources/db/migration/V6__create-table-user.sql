CREATE TABLE `user` (
    `id` int unsigned auto_increment,
    `name` varchar(50),
    `email` varchar(150) unique,
    `phone` char(15),
    `password` varchar(150),
    `address` int unsigned null,
    `created_at` datetime,
    `updated_at` datetime on update current_timestamp ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`address`) REFERENCES `address`(`id`)
);
