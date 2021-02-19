CREATE DATABASE IF NOT EXISTS esdb;
USE esdb;

CREATE TABLE IF NOT EXISTS `company`
(
 `company_id`    integer NOT NULL,
 `company_name`  varchar(45) NOT NULL ,
 `regular_hours` json NOT NULL ,

PRIMARY KEY (`company_id`)
);

CREATE TABLE IF NOT EXISTS `employee`
(
 `employee_id`       integer NOT NULL auto_increment ,
 `employee_name`     varchar(45) NOT NULL ,
 `company_id`        integer NOT NULL ,
 `username`          varchar(45) NOT NULL ,
 `password`          varchar(45) NOT NULL ,
 `available_hours`   json NOT NULL ,
 `employee_type`     bit NOT NULL ,
 `other_constraints` json NOT NULL ,
 `manager_access`    bit NOT NULL ,

PRIMARY KEY (`employee_id`),
KEY `fkIdx_36` (`company_id`),
CONSTRAINT `FK_35` FOREIGN KEY `fkIdx_36` (`company_id`) REFERENCES `company` (`company_id`)
);

CREATE TABLE IF NOT EXISTS `exception_times` (
    `et_id` INTEGER NOT NULL AUTO_INCREMENT,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `company_id` INTEGER NULL,
    `employee_id` INTEGER NULL,
    PRIMARY KEY (`et_id`),
    KEY `fkIdx_54` (`company_id`),
    CONSTRAINT `FK_53` FOREIGN KEY (`company_id`)
        REFERENCES `company` (`company_id`),
    KEY `fkIdx_61` (`employee_id`),
    CONSTRAINT `FK_60` FOREIGN KEY (`employee_id`)
        REFERENCES `employee` (`employee_id`)
);


CREATE TABLE IF NOT EXISTS `shift` (
    `shift_id` INTEGER NOT NULL AUTO_INCREMENT,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `check_in` DATETIME NULL,
    `check_out` DATETIME NULL,
    `notes` VARCHAR(45) NULL,
    `employee_id_manager` INTEGER NOT NULL,
    `employee_id_worker` INTEGER NOT NULL,
    PRIMARY KEY (`shift_id`),
    KEY `fkIdx_68` (`employee_id_manager`),
    CONSTRAINT `FK_67` FOREIGN KEY (`employee_id_manager`)
        REFERENCES `employee` (`employee_id`),
    KEY `fkIdx_86` (`employee_id_worker`),
    CONSTRAINT `FK_85` FOREIGN KEY (`employee_id_worker`)
        REFERENCES `employee` (`employee_id`)
);
