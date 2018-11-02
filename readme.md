# reactive demo for project tracking and timesheets with spring boot 2 and webflux 
Demo / Sample application to explore reactive programming with spring boot 2 / webflux

Apart from being a learning project, there's actual practical use. Run the service and
use one of the clients to record what tasks you have done on which project.

Generate reports based on client, project, timespan or their combinations.

In a far future, a minimized (jigsaw) executable jar shall be deployable on aws, a dockerized version might also come.

# Specs
## The application in a nutshell
A tool to be used by freelancers or consultants to keep track of their projects and time worked on tasks for those. A client has multiple projects, each projects has multiple tasks, each task has multiple entries. The reports from those entries can be used to create invoices or client-specific timesheets as proof-of-work. 

## use cases
- register new client
- register new project to existing client
- register new task to existing project
- register new taskentry to taks (this is the time(s) worked on a task)
- get report per project
- get report per client
- get report for all projects
- all reports shall be able to be restricted to a time (from / to) 

## requirements environment
- AWS deployable
- mongodb or other nosql storage for persistence
- java 8+

## nonfunctional requirements
- reactive / SpringBoot 2 (Spring 5)
- clean structure / architecture

## project structure
- reactive-service: contains the described springboot 2 / spring 5 reactive backend service
- client-lib: contains the code to connect to the service
- client-console: contains a client application (also springboot 2), that communicates with the service
- client-web: a web-ui, using static html plus js magic

## future requirements, might never come
- minimal artifact (jigsaw)
- multi user 
- spring security
- immutable models, might come later though
- fancy gui
- no coupling to vendor tech (it's spring, and it stays in spring)

## comments 
Nothing's ever perfect. Feedback is welcome! 
