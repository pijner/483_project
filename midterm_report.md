---
author: 
 - "Travis MacDonald"
 - "Prahar Ijner"
date: Feb 26, 2021
geometry: margin=2.54cm
output: pdf_document
fontfamily: sans
urlcolor: blue
---

# EasyShift Midterm Report
Midterm report for CSCI 483. Written by Travis MacDonald and Prahar Ijner.

## Introduction

## Website Design
The EasyShift web application is being developed on JDK 11 and Java EE 8.0 API on the JSF framework. A major component of this project involves user interaction, which is facilitated by \href{https://www.primefaces.org/showcase/index.xhtml?jfwid=e6cde}{PrimeFaces}. For our project, we have chosen PrimeFaces 10.0.0-rc1, which is available via the Maven Central repository. It is an extension of the last stable release of PrimeFaces 8.0 and fully compatible with it. The extension offers better user interaction and more modern-looking elements for the webpage.
The application currently runs on the \href{https://www.payara.fish/products/payara-server/}{Payara 5.201} application server and uses \href{https://www.mysql.com/products/enterprise/database/}{MySQL 8.0} as the database.

### Workflow
The workflow shows the sequence of events that will be processed by the current implementation based on user interaction. It should be noted that the workflow is minimal and does not include all the planned features for the project. Moreover, some aspects of the workflow (registration and updating database) are in the testing phase and have not been pushed to the repository.

The current state of the website includes a login page and a dashboard. The login page is where the user lands when the web app is launched. It is where existing users can log in, and on successful login, it takes them to their dashboard. If login fails, the login page is re-loaded.

![Planned workflow](resources/workflow_light.jpg){width=70% style="margin: auto;"}

### Login page
The login page consists of a panel that includes a text field to receive the username, and a password field to receive
the password. The submit button is implemented using a command button. When clicked, it establishes a connection with the database and queries it to check if the username and password match any record in the employee table of the database. If a match is found, the user is led to the dashboard page.

![Components of login](resources/login_components.jpg){width=50% style="margin: auto;"}

### Dashboard
The dashboard is the first page the user will see after a successful login. It displays the account holder’s name, company, and their manager’s ID, however, more details will be added as the project progresses. By default, it queries the database for shifts and availability of the entire team for 7 days from the current date. This is so that the response time is not very long, and the memory usage is kept to a minimum while ensuring sufficient details are loaded. The shifts and availiblity are displayed using a custom timeline widget from PrimeFaces.

The widget has been customized using a style sheet for a better visual presentation and distinguishing between availability and scheduled events on the timeline. The timeline is also customized to zoom into the events of the current day as we found it was more convenient to view current events and the time markers are more specific at this zoom level. The can view the other loaded shifts using the arrow buttons at the top right corner of the timeline widget. The future iteration of this will involve querying the database for shifts and availability when the arrows are clicked.

![Components of dashboard](resources/dashboard_components.jpg){width=100% style="margin: auto;"}


### Database
For this web application, we will be using MySQL 8.0 as the host server for our database to store information about employees, companies, and shifts. The current schema of the database is shown below. The database is connected to the web application using JDBC, available as a part of the MySQL package for Java (see DBConnector.java for implementation). The current version of the application established an insecure connection to the database, but the future version of the project is planned to establish SSL connection with server certificate validation. It

should be noted that regardless of the SSL unavailability, the passwords are still stored with SHA224 encryption. 

The employee table stores details about the user like their name, username, password, hours they are available to work (available_hours), any other constraints or conditions they have, whether or not they have manager level access, and who their manager is, and other administrative details.

The company table stores the name of the company and the operating hours.

The exception times table is exclusively to hold days employees or companies are not operating. This is to store
one-time events only and not occurrences like an employee is not available every Friday.

Finally, the shift table holds the shifts scheduled at a company for an employee. We have included an option for
check in time and check out time which can be used with the planned payroll system. The manager can also leave
notes for an employee for a given shift. For example: the shipment is coming in at 4 pm instead of 5 pm.

![Database schema](resources/db_schema.jpg){width=70% style="margin: auto;"}

## Conclusion and Discussion
