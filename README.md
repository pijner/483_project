# EasyShift

    CSCI 483 Project
    To be developed by
    - Travis MacDonald
    - Prahar Ijner

## The problem 
Task scheduling is an activity that occurs in most businesses on a regular basis. The most obvious example of this would be scheduling full-time and part-time shifts for employees at a store and creating an optimal schedule could result in better employee satisfaction, customer satisfaction, and/or minimized costs. However, creating an optimal schedule involves several considerations like ensuring there is at least one experienced employee working on any given shift, accommodating everyone’s availability, selecting which employees should work together to get the best *results*. All in all, it is a time-consuming process.

## The solution
Task scheduling is a classic computing problem used to service jobs on a CPU. This uses algorithms like first come first serve (FCFS), priority scheduling, and round robin. We propose using the concepts of these algorithms to develop a website that creates the schedule for you, given the tasks/employees in hand, constraints of each employee, possible associations and dependencies between employee shifts, and an optimization criterion, i.e., the cost.

The goal of using a web platform for this implementation is primarily to make the service accessible across OS platforms and potentially have user accounts for each manager and their employees so the employees can set their constraints for any given week for time off, subject to approval from the manager.

## UI

### PrimeFaces Components

- [Schedule](https://www.primefaces.org/showcase/ui/data/schedule/basic.xhtml?jfwid=a3fe3)
- [Timeline](https://www.primefaces.org/showcase/ui/data/timeline/allEvents.xhtml?jfwid=a3fe3)

