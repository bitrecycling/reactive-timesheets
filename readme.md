# reactive timesheet service with spring boot 2 and webflux
##  track and report your projects and activities  

Started as a learning project, to explore reactive programming. Run the service and
use one of the clients to record what tasks you have done on which project.

Generate reports based on client, project, timespan or their combinations. This is likely to be
implemented in either the client-lib or the clients. The service will however provide all the
necessary data and queries.

In a future version, a minimized (jigsaw) executable jar shall be generated to be deployed to "the cloud" or as a
containerized version (using docker or boxfuse). 

## The application in a nutshell
A tool to be used by freelancers or consultants to keep track of their projects and time worked on tasks for those. A client has multiple projects, each projects has multiple tasks, each task has multiple entries. The reports from those entries can be used to create invoices or client-specific timesheets as proof-of-work. 

## use cases
- register new client: the company you work for
- register new project to existing client: a project you work on for a client
- register new task to existing project: an activity or task you are doing for that project
- register new taskentry to taks: this is the time(s) worked on a task
- get report per project: gather all tasks and taskentries for a specific project
- get report per client: gather all projects and their tasks and taskentries for a client
- all reports shall be able to be restricted to a time (from / to) 

## how to run (locally for now)
- clone the repo and build, install and start mongodb, run the jar in /target

## more information on installation, design, implementation
See the wiki for the details

## comments 
Nothing's ever perfect. Feedback is welcome! 
