-- Company insertion
INSERT into esdb.company (`company_name`, `regular_hours`) VALUES (
	"Test company",
    '{
		"Monday": "08:00-17:00",
		"Tuesday": "08:00-17:00",
        "Wednesday": "08:00-17:00",
        "Thursday": "08:00-17:00",
        "Friday": "08:00-17:00",
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
    "iamboss123",
    '{
		"Monday": "08:00-17:00",
		"Tuesday": "08:00-17:00",
        "Wednesday": "08:00-17:00",
        "Thursday": "08:00-17:00",
        "Friday": "08:00-17:00",
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
    "iamjhurst123",
    '{
		"Monday": "08:00-17:00",
		"Tuesday": "08:00-17:00",
        "Wednesday": "",
        "Thursday": "08:00-17:00",
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
    "iamkgrimest123",
    '{
		"Monday": "",
		"Tuesday": "08:00-17:00",
        "Wednesday": "",
        "Thursday": "08:00-17:00",
        "Friday": "08:00-17:00",
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
    "iamswelch123",
    '{
		"Monday": "10:00-13:00",
		"Tuesday": "10:00-13:00",
        "Wednesday": "10:00-17:00",
        "Thursday": "13:00-17:00",
        "Friday": "13:00-17:00",
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
    "iamdmckinney123",
    '{
		"Monday": "08:00-17:00",
		"Tuesday": "08:00-17:00",
        "Wednesday": "08:00-17:00",
        "Thursday": "08:00-17:00",
        "Friday": "08:00-17:00",
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