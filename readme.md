# a time tracking and reporting service with spring boot 
## about  

Started as a learning project to explore reactive programming. Current incarnation does not include reactive-ness as in spring-webflux (or project reactor) anymore. Run the service and
use one of the clients to record the activities and the time spent on projects.

## The application in a nutshell
A tool to keep track of time spent on activities and to generate reports on them on demand. The reports can be used to create invoices or client-specific timesheets as proof-of-work.

See the data-model to judge if it's applicable or useful to you 

### data model
A simple hierarchical model, root is client
client 1->n project 1->n activity 1->n activity entry

In words: a client has multiple projects, which has multiple activities which have multiple activity entries.
Very few properties:
- client has name and address
- project has name, description and a rate (just an int, no currency, application has to deal)
- activity has a name 
- activity entry has a start time and a duration

### reporting
query the report-endpoint to generate activity-entry reports based on client, project, timespan or their combinations.
The client / application can create a monthly report by requesting all activity entries that started in the desired report-month.
Together with the client / project and activity information a timesheet report can be generated.  

### use cases
- register new client: the company you work for
- register new project to existing client: a project you work on for a client
- register new activity to existing project: an activity you are doing for that project
- register new activity entry to activity: this is the time(s) worked on a task
- get report per project: gather all tasks and taskentries for a specific project
- get report per client: gather all projects and their tasks and taskentries for a client
- all reports shall be able to be restricted to a time (from / to) 



### web-client
There is a web client (SPA) included, that showcases the service.

<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_dashboard.png" width="400" />
<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_track_time.png" width="400" />    
<img src="https://raw.githubusercontent.com/bitrecycling/reactive-timesheets-service/develop/readme/Screenshot_report.png" width="400" />    

## how to run
- clone the repo
- build and run with `mvnw clean install spring-boot:run -DskipTests`
- open browser and go to `http://hostname:8080` to access the webclient

## comments 
Nothing's ever perfect. Feedback is welcome at @bitrecycling on twitter
