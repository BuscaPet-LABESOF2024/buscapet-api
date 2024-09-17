CREATE TABLE `address` (
   `id` int unsigned auto_increment,
   `street` varchar(150),
   `number` int,
   `neighborhood` varchar(100),
   `cep` char(11) null,
   `created_at` datetime,
   `updated_at` datetime on update current_timestamp ,
   PRIMARY KEY (`id`)
);