CREATE TABLE `image_announcement` (
      `id` int unsigned auto_increment,
      `image` blob,
      `announcement` int unsigned,
      `created_at` datetime,
      `updated_at` datetime on update current_timestamp,
      PRIMARY KEY (`id`),
      FOREIGN KEY (`announcement`) REFERENCES `announcement`(`id`)
);

