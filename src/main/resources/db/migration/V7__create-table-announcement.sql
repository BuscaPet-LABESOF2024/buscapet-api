CREATE TABLE `announcement` (
    `id` int unsigned auto_increment,
    `title` varchar(50),
    `description` text,
    `animal` int unsigned,
    `contact_phone` varchar(15),
    `contact_email` varchar(100) null,
    `user` int unsigned,
    `announcement_type` int unsigned,
    `active` tinyint,
    `created_at` datetime,
    `updated_at` datetime on update current_timestamp ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`animal`) REFERENCES `animal`(`id`),
    FOREIGN KEY (`announcement_type`) REFERENCES `announcement_type`(`id`),
    FOREIGN KEY (`user`) REFERENCES `user`(`id`)
);

