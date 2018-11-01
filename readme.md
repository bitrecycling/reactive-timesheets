# timeshizz
reactive timesheet service with springboot 2 / webflux

This is a demo and learning repo to explore the depths of reactive programming and springboot 2 with webflux enabled webapp. In a far future, a minimized (jigsaw) executable jar shall be deployable on aws, a dockerized version might also come.

## The application in a nutshell
A tool to be used by freelancers or consultants to keep track of their projects and time worked on tasks for those. A client has multiple projects, each projects has multiple tasks, each task has multiple entries. The reports from those entries can be used to create invoices or client-specific timesheets as proof-of-work. 

### environment
- AWS deployed / deployable
- minimal artifact (jigsaw)
- mongodb

### nonfunctional requirements
- reactive / SpringBoot 2 (Spring 5)

### functional requirements
- register new client
- register new project to existing client
- register new task to existing project
- register new taskentry to taks (this is the time(s) worked on a task)
- get report per project
- get report per client
- get report for all projects
- all reports shall be able to be restricted to a time (from / to) 

### project structure
- reactive-service: contains the described springboot 2 / spring 5 reactive backend service
- client-lib: contains the code to connect to the service
- client-console: contains a client application (also springboot 2), that communicates with the service
- client-web: a web-ui, using static html plus js magic

## comments 
Nothing's ever perfect. Feedback is welcome! 
