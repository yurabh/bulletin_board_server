CREATE DATABASE bulletin_board;
DROP DATABASE bulletin_board;


DROP DATABASE bulletin_board_test;
CREATE DATABASE bulletin_board_test;


CREATE TABLE IF NOT EXISTS `headings` (
`heading_id` INT(11) AUTO_INCREMENT PRIMARY KEY,
`version` INT(11),
`name` VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS `announcements` (
`announcement_id` INT AUTO_INCREMENT PRIMARY KEY,
`active` BIT(1) NOT NULL,
`name` VARCHAR(255) NOT NULL,
`publication_date` DATE NOT NULL,
`revelation_text` VARCHAR(255) NOT NULL,
`service_cost` DECIMAL(19,5) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
`heading_fk_id` INT(11),
CONSTRAINT `announcement_fkey_id`
FOREIGN KEY (`heading_fk_id`)
REFERENCES headings(`heading_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT,
CONSTRAINT `announcement_fk_id`
FOREIGN KEY(`author_fk_id`)
REFERENCES authors(`author_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS `authors` (
`author_id` INT AUTO_INCREMENT NOT NULL,
`name`VARCHAR (255) NOT NULL,
`active` BIT(1) NOT NULL,
`lastName` VARCHAR (50) NOT NULL,
`password`VARCHAR (255) NOT NULL,
`version`INT(11) DEFAULT 0,
PRIMARY KEY(`author_id`)
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `roles`(
`role_id` INT(11) AUTO_INCREMENT,
`role` INT(11) NOT NULL,
PRIMARY KEY(`role_id`)
)ENGINE=InnoDb DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `user_role`(
`user_id` INT(11),
`role_id` INT(11),
FOREIGN KEY (`user_id`) REFERENCES authors (`author_id`)
ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY (`role_id`) REFERENCES roles (`role_id`)
 ON DELETE RESTRICT ON UPDATE CASCADE,
PRIMARY KEY (`user_id`, `role_id`)
)ENGINE=InnoDb DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `addresses`(
 `address_id` INT AUTO_INCREMENT,
 `address` VARCHAR(255) NOT NULL,
 `version` INT(11) NOT NULL,
 `author_fk_id` INT(11),
 PRIMARY KEY(`address_id`),
 CONSTRAINT `author_fk_id`
 FOREIGN KEY (`author_fk_id`)
 REFERENCES authors(`author_id`)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `emails`(
`email_id` INT AUTO_INCREMENT NOT NULL,
`emailAuthor` VARCHAR(255) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
PRIMARY KEY(`email_id`),
CONSTRAINT `email_fk_id`
FOREIGN KEY(`author_fk_id`)
REFERENCES authors(`author_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `phones`(
`phone_id` INT AUTO_INCREMENT,
`number` VARCHAR(255) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
PRIMARY KEY(`phone_id`),
CONSTRAINT `phone_fk_id`
FOREIGN KEY(`author_fk_id`)
REFERENCES authors (`author_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE IF NOT EXISTS `suitableAds`(
`suitable_id` INT NOT NULL AUTO_INCREMENT,
`category` VARCHAR(255) NOT NULL,
`price_from` DECIMAL(19,2) NOT NULL,
`price_to` DECIMAL(19,2) NOT NULL,
`title` VARCHAR(255) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
PRIMARY KEY(`suitable_id`),
CONSTRAINT `suitable`
FOREIGN KEY(`author_fk_id`)
REFERENCES authors(`author_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;
