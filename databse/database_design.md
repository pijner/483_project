# Database design


## company
*Holds details about the company*
* **company_id**:	 	numeric – Primary key,
* company_name: 		varchar,
* regular_hours: 		json



## manager
*Holds manager details*
* **manager_ID**: 		numeric - Primary key, 
* *company_ID*:	 		numeric - Foreign key, 
* manager_name: 		varchar,
* username: 			varchar, 
* password: 			varchar


## employee
*Holds employee details, available times, and manager imposed constraints*
* **employee_id**: 		numeric – Primary key, 
* *manager_id*:  		numeric – Foreign key, 
* *company_id*:	  		numeric – Foreign key, 
* employee_name: 		varchar, 
* username: 			varchar, 
* password: 			varchar,
* available_hours:		json,
* other_constraints:	json - Can include constraints imposed by manager,
* type: 				bit – Part time of full time



## shift
*Holds shift details and check in/out info*
* **shift_id**:			numeric - Primary key,
* *employee_id*: 		numeric – Foreign key,
* *manager_id*:			numeric - Foreign key,
* start_time:	 		datetime,
* end_time:		 		datetime,
* check_in: 			datetime,
* check_out: 			datetime


## exception_times
*Exception times can be used to set closure days for a company (public holidays) or request days off by an employee*
* **et_id**:			numeric - Primary key,
* *company_id*:			numeric - Foreign key (NULL),
* *employee_id*:		numeric - Foreign key (NULL)
* start_time:			datetime,
* end_time:				datetime,
