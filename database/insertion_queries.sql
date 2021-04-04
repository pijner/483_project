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
	`password`          VARCHAR(255) NOT NULL,
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

-- Company insertion
INSERT into esdb.company (`company_name`, `regular_hours`) VALUES (
	"Test company",
    '{
		"Monday": "08:00:00-17:00:00",
		"Tuesday": "08:00:00-17:00:00",
        "Wednesday": "08:00:00-17:00:00",
        "Thursday": "08:00:00-17:00:00",
        "Friday": "08:00:00-17:00:00",
        "Saturday": "",
        "Sunday": ""
	}'
);


-- Employee insertion - Manager level access, full time employee;
INSERT into esdb.employee (
    `employee_name`, `company_id`, `username`, `password`, `available_hours`, `employee_type`, `other_constraints`, `manager_access`, `manager_id`
) VALUES (
    "Boss person",
    1,
    "bossperson",
    SHA2("iamboss123", 224),
    '{
		"Monday": "08:00:00-17:00:00",
		"Tuesday": "08:00:00-17:00:00",
        "Wednesday": "08:00:00-17:00:00",
        "Thursday": "08:00:00-17:00:00",
        "Friday": "08:00:00-17:00:00",
        "Saturday": "",
        "Sunday": ""
	}',
    1,
    "{}",
    1,
    1
);

-- Employee insertion - part time employee;
INSERT into esdb.employee (
    `employee_name`, `company_id`, `username`, `password`, `available_hours`, `employee_type`, `other_constraints`, `manager_access`, `manager_id`
) VALUES (
    "Jason Hurst",
    1,
    "jhurst",
    SHA2("iamjhurst123", 224),
    '{
		"Monday": "08:00:00-17:00:00",
		"Tuesday": "08:00:00-17:00:00",
        "Wednesday": "",
        "Thursday": "08:00:00-17:00:00",
        "Friday": "",
        "Saturday": "",
        "Sunday": ""
	}',
    0,
    "{}",
    0,
    1
);

-- Employee insertion - part time employee;
INSERT into esdb.employee (
    `employee_name`, `company_id`, `username`, `password`, `available_hours`, `employee_type`, `other_constraints`, `manager_access`, `manager_id`
) VALUES (
    "Kierra Grimes",
    1,
    "kgrimes",
    SHA2("iamkgrimest123", 224),
    '{
		"Monday": "",
		"Tuesday": "08:00:00-17:00:00",
        "Wednesday": "",
        "Thursday": "08:00:00-17:00:00",
        "Friday": "08:00:00-17:00:00",
        "Saturday": "",
        "Sunday": ""
	}',
    0,
    "{}",
    0,
    1
);

-- Employee insertion - part time employee;
INSERT into esdb.employee (
    `employee_name`, `company_id`, `username`, `password`, `available_hours`, `employee_type`, `other_constraints`, `manager_access`, `manager_id`
) VALUES (
    "Sydnee Welch",
    1,
    "swelch",
    SHA2("iamswelch123", 224),
    '{
		"Monday": "10:00:00-13:00:00",
		"Tuesday": "10:00:00-13:00:00",
        "Wednesday": "10:00:00-17:00:00",
        "Thursday": "13:00:00-17:00:00",
        "Friday": "13:00:00-17:00:00",
        "Saturday": "",
        "Sunday": ""
	}',
    0,
    "{}",
    0,
    1
);


-- Employee insertion - full time employee;
INSERT into esdb.employee (
    `employee_name`, `company_id`, `username`, `password`, `available_hours`, `employee_type`, `other_constraints`, `manager_access`, `manager_id`
) VALUES (
    "Darwin Mckinney",
    1,
    "dmckinney",
    SHA2("iamdmckinney123", 224),
    '{
		"Monday": "08:00:00-17:00:00",
		"Tuesday": "08:00:00-17:00:00",
        "Wednesday": "08:00:00-17:00:00",
        "Thursday": "08:00:00-17:00:00",
        "Friday": "08:00:00-17:00:00",
        "Saturday": "",
        "Sunday": ""
	}',
    1,
    "{}",
    0,
    1
);


-- Shift insertion (only for midterm demo. Normally shift insertion should only take place through the program)
-- All shifts for boss person
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-22 08:00:00',
    '2021-02-22 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-23 08:00:00',
    '2021-02-23 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-24 08:00:00',
    '2021-02-24 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-25 08:00:00',
    '2021-02-25 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-26 08:00:00',
    '2021-02-26 17:00:00',
    1,
    1
);

-- all shifts for jhurst
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-22 08:00:00',
    '2021-02-22 17:00:00',
    1,
    2
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-25 08:00:00',
    '2021-02-25 13:00:00',
    1,
    2
);

-- all shifts for kgrimes
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-23 12:00:00',
    '2021-02-23 17:00:00',
    1,
    3
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-26 08:00:00',
    '2021-02-26 17:00:00',
    1,
    3
);

-- all shifts for swelch
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-23 10:00:00',
    '2021-02-23 13:00:00',
    1,
    4
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-24 10:00:00',
    '2021-02-24 17:00:00',
    1,
    4
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-25 13:00:00',
    '2021-02-25 17:00:00',
    1,
    4
);

-- all shifts for dmckinney
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-22 08:00:00',
    '2021-02-22 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-23 08:00:00',
    '2021-02-23 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-24 08:00:00',
    '2021-02-24 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-25 08:00:00',
    '2021-02-25 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-02-26 08:00:00',
    '2021-02-26 17:00:00',
    1,
    5
);


-- next week's shifts
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-01 08:00:00',
    '2021-03-01 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-02 08:00:00',
    '2021-03-02 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-03 08:00:00',
    '2021-03-03 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-04 08:00:00',
    '2021-03-04 17:00:00',
    1,
    1
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-05 08:00:00',
    '2021-03-05 17:00:00',
    1,
    1
);

-- all shifts for jhurst
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-01 08:00:00',
    '2021-03-01 17:00:00',
    1,
    2
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-04 08:00:00',
    '2021-03-04 13:00:00',
    1,
    2
);

-- all shifts for kgrimes
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-02 12:00:00',
    '2021-03-02 17:00:00',
    1,
    3
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-05 08:00:00',
    '2021-03-05 17:00:00',
    1,
    3
);

-- all shifts for swelch
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-02 10:00:00',
    '2021-03-02 13:00:00',
    1,
    4
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-03 10:00:00',
    '2021-03-03 17:00:00',
    1,
    4
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-04 13:00:00',
    '2021-03-04 17:00:00',
    1,
    4
);

-- all shifts for dmckinney
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-01 08:00:00',
    '2021-03-01 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-02 08:00:00',
    '2021-03-02 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-03 08:00:00',
    '2021-03-03 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-04 08:00:00',
    '2021-03-04 17:00:00',
    1,
    5
);
INSERT into esdb.shift (
    `start_time`, `end_time`, `employee_id_manager`, `employee_id_worker`
) VALUES (
    '2021-03-05 08:00:00',
    '2021-03-05 17:00:00',
    1,
    5
);
