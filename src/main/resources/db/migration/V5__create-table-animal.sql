CREATE TABLE `animal` (
      `id` int unsigned auto_increment,
      `name` varchar(50) null,
      `status_animal` int unsigned,
      `type` varchar(100),
      `breed` varchar(100) null,
      `size_animal` int unsigned,
      `weight` decimal(5,2) null,
      `age` int null,
      `created_at` datetime,
      `updated_at` datetime on update current_timestamp ,
      PRIMARY KEY (`id`),
      FOREIGN KEY (`status_animal`) REFERENCES `status_animal`(`id`),
      FOREIGN KEY (`size_animal`) REFERENCES `size_animal`(`id`)
);
