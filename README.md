# EasyShift

CSCI 483 Project
In development by 
- [Travis MacDonald](https://github.com/travismacdonald)
- [Prahar Ijner](https://github.com/pijner)

Final report can be found in `docs/final_report.pdf`

## The problem 
Task scheduling is an activity that occurs in most businesses on a regular basis. The most obvious example of this would be scheduling full-time and part-time shifts for employees at a store and creating an optimal schedule could result in better employee satisfaction, customer satisfaction, and/or minimized costs. However, creating an optimal schedule involves several considerations like ensuring there is at least one experienced employee working on any given shift, accommodating everyone’s availability, selecting which employees should work together to get the best *results*. All in all, it is a time-consuming process.

## The solution
Task scheduling is a classic computing problem used to service jobs on a CPU. This uses algorithms like first come first serve (FCFS), priority scheduling, and round robin. We propose using the concepts of these algorithms to develop a website that creates the schedule for you, given the tasks/employees in hand, constraints of each employee, possible associations and dependencies between employee shifts, and an optimization criterion, i.e., the cost.

The goal of using a web platform for this implementation is primarily to make the service accessible across OS platforms and potentially have user accounts for each manager and their employees so the employees can set their constraints for any given week for time off, subject to approval from the manager.

## UI

`TODO`

### PrimeFaces Components

- [Schedule](https://www.primefaces.org/showcase/ui/data/schedule/basic.xhtml?jfwid=a3fe3)
- [Timeline](https://www.primefaces.org/showcase/ui/data/timeline/allEvents.xhtml?jfwid=a3fe3)

## Docker run
- Open the main directory in terminal
- Type the command `docker-compose up`
- Once the servers are up and running, open a browser and go to [`http://localhost:8080/easyshift-1.0/`](http://localhost:8080/easyshift-1.0/)
- Run `docker-compose down` to stop servers

## Notes
- The mock data in the sql files included only contains shifts up to March 5th, 2021. If you're running the project after that date, you may not see any shifts populate in the timeline. You should still see the availablity.
- The login information can be seen in `insertion_queries.sql` (or you can use the account bossperson with password iamboss123).
