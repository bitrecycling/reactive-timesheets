# reactive timesheet service with spring boot 2 and webflux
##  track and report your projects and activities  

Started as a learning project to explore reactive programming. Run the service and
use one of the clients to record the tasks and the time spent on projects.

Generate reports based on client, project, timespan or their combinations. This is likely to be
implemented in either the client-lib or the clients. The service will however provide all the
necessary data and queries.

There is a web client (SPA) included, that showcases the service. Also there's a swagger-ui to get an overview
and test single calls.

In a future version, a minimized (jigsaw) executable jar shall be generated to be deployed to "the cloud" or as a
containerized version (using docker or boxfuse). 

<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_dashboard.png" width="400" />
<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_track_time.png" width="400" />    
<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_report.png" width="400" />    

## The application in a nutshell
A tool to be used by freelancers or consultants to keep track of their projects and time worked on tasks for those.
A client has multiple projects, each projects has multiple tasks, each task has multiple entries. The reports from those entries can be used to create invoices or client-specific timesheets as proof-of-work. 

## use cases
- register new client: the company you work for
- register new project to existing client: a project you work on for a client
- register new task to existing project: an activity or task you are doing for that project
- register new taskentry to taks: this is the time(s) worked on a task
- get report per project: gather all tasks and taskentries for a specific project
- get report per client: gather all projects and their tasks and taskentries for a client
- all reports shall be able to be restricted to a time (from / to) 

## how to run
+ install and start mongodb
+ clone the repo
+ set environment vars to point to your mongo installation:
   + `export MONGODB_DATABASE=<database name you want to use>`
   + `export MONGODB_HOST=<hostname or ip address of you mongodb>`
   + `export MONGODB_HOST=<port mongodb listens to, default for mongo: 27017>`
- build and run with `mvnw clean install spring-boot:run -DskipTests`
- open browser and go to `http://hostname:8080` to access the webclient
- open browser and go to `http://hostname:8080/swagger-ui` to access the swagger ui

## run on aws
+ create 1 ec2 instance for mongodb (e.g. t2 micro with amazon linux)
+ install mongodb (e.g. using yum) on that instance
+ adapt mongodb config to listen to ip (change 127.0.0.1 to 0.0.0.0)
+ build reactive timesheets
+ create new java application with elastic beanstalk (this automatically creates another ec2 instance)
+ upload jar from target dir
+ adapt configuration and add "SERVER_PORT", value 5000
+ start application (takes a while)
+ wire ec2 instances: adapt mongodb instance security group to accept inbound traffic on mongodb port (usually 27017)
from the security group or internal IP from the ec2 instance you installed reactive timesheets on 

## more information
See the wiki for some more details

## comments 
Nothing's ever perfect. Feedback is welcome at @bitrecycling on twitter