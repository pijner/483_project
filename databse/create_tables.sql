CREATE DATABASE IF NOT EXISTS esdb;
USE esdb;

CREATE TABLE IF NOT EXISTS `company`
(
	`company_name`  VARCHAR(45) NOT NULL,
    `regular_hours` JSON NOT NULL,
    `company_id`    INTEGER AUTO_INCREMENT,
 
	PRIMARY KEY (`company_id`)
);

CREATE TABLE IF NOT EXISTS `employee`
(
	`employee_name`     VARCHAR(45) NOT NULL,
	`company_id`        INTEGER NOT NULL,
	`username`          VARCHAR(45) NOT NULL,
	`password`          VARCHAR(45) NOT NULL,
	`available_hours`   JSON NOT NULL,
	`employee_type`     BIT NOT NULL,
	`other_constraints` JSON NOT NULL,
	`manager_access`    BIT NOT NULL,
	`employee_id`       INTEGER AUTO_INCREMENT,
    `manager_id`        INTEGER NOT NULL ,

	PRIMARY KEY (`employee_id`),
	KEY `fkIdx_36` (`company_id`),
	CONSTRAINT `FK_35` FOREIGN KEY `fkIdx_36` (`company_id`) REFERENCES `company` (`company_id`),
    KEY `fkIdx_89` (`manager_id`),
    CONSTRAINT `FK_88` FOREIGN KEY `fkIdx_89` (`manager_id`) REFERENCES `employee` (`employee_id`)
);

CREATE TABLE IF NOT EXISTS `exception_times` (
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `company_id` INTEGER NULL,
    `employee_id` INTEGER NULL,
    `et_id` INTEGER AUTO_INCREMENT,
    
    PRIMARY KEY (`et_id`),
    KEY `fkIdx_54` (`company_id`),
    CONSTRAINT `FK_53` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
    KEY `fkIdx_61` (`employee_id`),
    CONSTRAINT `FK_60` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
);


CREATE TABLE IF NOT EXISTS `shift` (
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `check_in` DATETIME NULL,
    `check_out` DATETIME NULL,
    `notes` VARCHAR(45) NULL,
    `employee_id_manager` INTEGER NOT NULL,
    `employee_id_worker` INTEGER NOT NULL,
    `shift_id` INTEGER AUTO_INCREMENT,
    
    PRIMARY KEY (`shift_id`),
    KEY `fkIdx_68` (`employee_id_manager`),
    CONSTRAINT `FK_67` FOREIGN KEY (`employee_id_manager`) REFERENCES `employee` (`employee_id`),
    KEY `fkIdx_86` (`employee_id_worker`),
    CONSTRAINT `FK_85` FOREIGN KEY (`employee_id_worker`) REFERENCES `employee` (`employee_id`)
);
