INSERT INTO `size_animal` (`description`) VALUES
('Small'), ('Medium'), ('Large');

INSERT INTO `status_animal` (`description`) VALUES
('Lost'), ('Found'), ('Available for Adoption');

INSERT INTO `announcement_type` (`description`) VALUES
('Lost'), ('Found'), ('Available for Adoption');

INSERT INTO `address` (`street`, `number`, `neighborhood`, `cep`, `created_at`) VALUES
('123 Main St', 101, 'Downtown', '12345-678', NOW()),
('456 Elm St', 202, 'Uptown', '23456-789', NOW()),
('789 Oak St', 303, 'Midtown', '34567-890', NOW()),
('101 Pine St', 404, 'Suburb', '45678-901', NOW()),
('202 Maple St', 505, 'Riverside', '56789-012', NOW()),
('303 Cedar St', 606, 'Hilltop', '67890-123', NOW()),
('404 Birch St', 707, 'Lakeside', '78901-234', NOW()),
('505 Willow St', 808, 'Valley', '89012-345', NOW()),
('606 Spruce St', 909, 'Mountain', '90123-456', NOW()),
('707 Fir St', 1010, 'Forest', '01234-567', NOW()),
('123 Maple Ave', 303, 'Parkview', '12345-678', NOW()),
('456 Cedar Ave', 404, 'Greenfield', '23456-789', NOW());

INSERT INTO `animal` (`name`, `status_animal`, `type`, `breed`, `size_animal`, `weight`, `age`, `created_at`) VALUES
('Max', 1, 'Dog', 'Labrador', 3, 30.5, 5, NOW()),
('Bella', 2, 'Cat', 'Siamese', 2, 10.2, 3, NOW()),
('Charlie', 3, 'Dog', 'Beagle', 2, 15.4, 4, NOW()),
('Daisy', 1, 'Cat', 'Persian', 1, 8.1, 2, NOW()),
('Rocky', 2, 'Dog', 'Bulldog', 3, 25.6, 6, NOW()),
('Luna', 3, 'Cat', 'Maine Coon', 3, 12.3, 4, NOW()),
('Bear', 1, 'Dog', 'Rottweiler', 3, 40.2, 5, NOW()),
('Molly', 2, 'Dog', 'Poodle', 2, 11.5, 3, NOW()),
('Oscar', 3, 'Cat', 'Bengal', 2, 9.7, 2, NOW()),
('Sophie', 1, 'Dog', 'Yorkshire Terrier', 1, 5.9, 1, NOW()),
('Coco', 2, 'Dog', 'Shih Tzu', 1, 6.5, 3, NOW()),
('Rex', 3, 'Cat', 'Sphynx', 2, 10.4, 4, NOW());

INSERT INTO `user` (`name`, `email`, `phone`, `password`, `address`, `created_at`) VALUES
('John Doe', 'john.doe@example.com', '123-456-7890', 'password123', 1, NOW()),
('Jane Smith', 'jane.smith@example.com', '234-567-8901', 'password123', 2, NOW()),
('Emily Johnson', 'emily.johnson@example.com', '345-678-9012', 'password123', 3, NOW()),
('Michael Brown', 'michael.brown@example.com', '456-789-0123', 'password123', 4, NOW()),
('Sarah Davis', 'sarah.davis@example.com', '567-890-1234', 'password123', 5, NOW()),
('David Wilson', 'david.wilson@example.com', '678-901-2345', 'password123', 6, NOW()),
('Laura Martinez', 'laura.martinez@example.com', '789-012-3456', 'password123', 7, NOW()),
('Daniel Garcia', 'daniel.garcia@example.com', '890-123-4567', 'password123', 8, NOW()),
('Sophia Rodriguez', 'sophia.rodriguez@example.com', '901-234-5678', 'password123', 9, NOW()),
('James Anderson', 'james.anderson@example.com', '012-345-6789', 'password123', 10, NOW()),
('Olivia Taylor', 'olivia.taylor@example.com', '345-678-9012', 'password123', 11, NOW()),
('Lucas Lee', 'lucas.lee@example.com', '456-789-0123', 'password123', 12, NOW());

INSERT INTO `announcement` (`title`, `description`, `animal`, `contact_phone`, `contact_email`, `user`, `announcement_type`, `active`, `created_at`) VALUES
('Lost Dog', 'Lost Labrador last seen in Downtown.', 1, '123-456-7890', 'john.doe@example.com', 1, 1, 1, NOW()),
('Found Cat', 'Found Siamese near Uptown.', 2, '234-567-8901', 'jane.smith@example.com', 2, 2, 1, NOW()),
('Available for Adoption', 'Beagle looking for a new home.', 3, '345-678-9012', 'emily.johnson@example.com', 3, 3, 1, NOW()),
('Looking for Foster', 'Persian cat needs a temporary home.', 4, '456-789-0123', 'michael.brown@example.com', 4, 2, 1, NOW()),
('Available for Sale', 'Bulldog for sale. Contact for more details.', 5, '567-890-1234', 'sarah.davis@example.com', 5, 1, 1, NOW()),
('Found Dog', 'Rottweiler found near Riverside.', 6, '678-901-2345', 'david.wilson@example.com', 6, 2, 1, NOW()),
('Available for Adoption', 'Poodle up for adoption.', 7, '789-012-3456', 'laura.martinez@example.com', 7, 3, 1, NOW());

INSERT INTO `image_announcement` (`image`, `announcement`, `created_at`) VALUES
(LOAD_FILE('/path/to/image1.jpg'), 1, NOW()),
(LOAD_FILE('/path/to/image2.jpg'), 2, NOW()),
(LOAD_FILE('/path/to/image3.jpg'), 3, NOW()),
(LOAD_FILE('/path/to/image4.jpg'), 4, NOW()),
(LOAD_FILE('/path/to/image5.jpg'), 5, NOW()),
(LOAD_FILE('/path/to/image48.jpg'), 6, NOW()),
(LOAD_FILE('/path/to/image49.jpg'), 7, NOW());
