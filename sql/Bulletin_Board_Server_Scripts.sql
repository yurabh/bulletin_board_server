CREATE DATABASE bulletin_board;
DROP DATABASE bulletin_board;

CREATE TABLE `authors` (
`author_id` INT AUTO_INCREMENT NOT NULL,
`name`VARCHAR(255) NOT NULL,
`version`INT DEFAULT 0,
PRIMARY KEY(`author_id`)
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE `addresses`(
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


CREATE TABLE `emails`(
`email_id` INT AUTO_INCREMENT NOT NULL,
`email` VARCHAR(255) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
PRIMARY KEY(`email_id`),
CONSTRAINT `email_fk_id`
FOREIGN KEY(`author_fk_id`)
REFERENCES authors(`author_id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE `phones`(
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


CREATE TABLE `announcements`(
`announcement_id` INT NOT NULL AUTO_INCREMENT,
`active` BIT(1) NOT NULL,
`name` VARCHAR(255) NOT NULL,
`publication_date` DATE NOT NULL,
`revelation_text` VARCHAR(255) NOT NULL,
`service_cost` DECIMAL(19,5) NOT NULL,
`version` INT(11) NOT NULL,
`author_fk_id` INT(11),
`heading_fk_id` INT(11),
PRIMARY KEY(`announcement_id`),
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
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE `headings`(
`heading_id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`version` INT(11) NOT NULL,
PRIMARY KEY(`heading_id`)
)ENGINE=InnoDB DEFAULT CHAR SET=utf8;


CREATE TABLE `suitableAds`(
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
