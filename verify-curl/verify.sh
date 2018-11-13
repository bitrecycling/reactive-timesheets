#!/usr/bin/env bash

CLIENT_ID=0
PROJECT_ID=0
TASK1_ID=0
TASK2_ID=0
TASK3_ID=0
TASKENTRY1_ID=0
TASKENTRY2_ID=0
TASKENTRY3_ID=0
TASKENTRY4_ID=0
TASKENTRY5_ID=0
TASKENTRY6_ID=0

START_TIME=`date "+%Y-%m-%dT%H:%M:%S"`

function createClient() {
    curl -s -X POST "localhost:8080/clients" -d "name=Bitrecylcing Enterprises&address=Bitstreet 42, 80000 Munich" -o clientresponse.tmp
}
function createProject() {
  echo "creating 1 project"
  CLIENT_ID=($(jq -r '.id' ./clientresponse.tmp))
  curl -s -X POST "localhost:8080/projects"  -d "name=Timesheet Service&description=Design an implement the service&rate=60.0&clientId=${CLIENT_ID}"  -o projectresponse.tmp
}
function createTasks(){
  PROJECT_ID=($(jq -r '.id' ./projectresponse.tmp))
  echo "creating 3 tasks"
  curl -s -X POST "localhost:8080/tasks"  -d "name=Design Service&projectId=${PROJECT_ID}"  -o taskresponse1.tmp
  curl -s -X POST "localhost:8080/tasks"  -d "name=Implement Service&projectId=${PROJECT_ID}"  -o taskresponse2.tmp
  curl -s -X POST "localhost:8080/tasks"  -d "name=Document Service&projectId=${PROJECT_ID}"  -o taskresponse3.tmp

}
function createTaskEntries1(){
  echo "creating 3 taskentries"
  TASK1_ID=($(jq -r '.id' ./taskresponse1.tmp))
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=300&taskId=${TASK1_ID}"  -o taskentryresponse1.tmp
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=20&taskId=${TASK1_ID}"  -o taskentryresponse2.tmp
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=600&taskId=${TASK1_ID}"  -o taskentryresponse3.tmp
}

function createTaskEntries2(){
  echo "creating 2 taskentries"
  TASK2_ID=($(jq -r '.id' ./taskresponse2.tmp))
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=300&taskId=${TASK2_ID}"  -o taskentryresponse4.tmp
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=50&taskId=${TASK2_ID}"  -o taskentryresponse5.tmp
}

function createTaskEntries3(){
  echo "creating 1 taskentry"
  TASK3_ID=($(jq -r '.id' ./taskresponse3.tmp))
  curl -s -X POST "localhost:8080/taskentries"  -d "startTime=${START_TIME}&durationMinutes=123&taskId=${TASK3_ID}"  -o taskentryresponse6.tmp
}

echo "creating test data..."
START=`date "+%Y-%m-%dT%H:%M:%S"`
createClient
createProject
TASK_START=`date "+%Y-%m-%dT%H:%M:%S"`
createTasks
TASKENTRY_1_START=`date "+%Y-%m-%dT%H:%M:%S"`
createTaskEntries1
sleep 1
TASKENTRY_2_START=`date "+%Y-%m-%dT%H:%M:%S"`
createTaskEntries2
sleep 1
TASKENTRY_3_START=`date "+%Y-%m-%dT%H:%M:%S"`
createTaskEntries3
sleep 1
END=`date "+%Y-%m-%dT%H:%M:%S"`

echo "created test data"

echo "querying test data"
echo "tasks created between ${START} and ${END}"
curl -s "http://localhost:8080/tasks?from=${START}&to=${END}" | jq length
echo "taskentries created between ${START} and ${END}"
curl -s "http://localhost:8080/taskentries?from=${START}&to=${END}" | jq length
echo "taskentries created between ${TASKENTRY_1_START} and ${TASKENTRY_2_START}"
curl -s "http://localhost:8080/taskentries?from=${TASKENTRY_1_START}&to=${TASKENTRY_2_START}" | jq length
echo "taskentries created between ${TASKENTRY_2_START} and ${TASKENTRY_3_START}"
curl -s "http://localhost:8080/taskentries?from=${TASKENTRY_2_START}&to=${TASKENTRY_3_START}" | jq length
echo "taskentries created between ${TASKENTRY_3_START} and ${END}"
curl -s "http://localhost:8080/taskentries?from=${TASKENTRY_3_START}&to=${END}" | jq length
echo "taskentries created for task ${TASK1_ID}"
curl -s "http://localhost:8080/taskentries?taskId=${TASK1_ID}" | jq length
echo "taskentries created for task ${TASK2_ID}"
curl -s "http://localhost:8080/taskentries?taskId=${TASK2_ID}" | jq length
echo "taskentries created for task ${TASK3_ID}"
curl -s "http://localhost:8080/taskentries?taskId=${TASK3_ID}" | jq length

PROJECT_NAME=`curl -s "http://localhost:8080/projects/${PROJECT_ID}" | jq '.name'`
RATE=`curl -s "http://localhost:8080/projects/${PROJECT_ID}" | jq '.rate'`
echo "REPORT FOR PROJECT: ${PROJECT_NAME}"
echo "RATE=" ${RATE}

PROJECT_TASK_NAMES_IDS=`curl -s "http://localhost:8080/tasks?projectId=${PROJECT_ID}" | jq -r '. |.[] |"\(.id) \(.name)"'`

function printTaskEntries(){
    IFS=' ' read -ra yo <<< "$1"
    echo "Task '${yo[1]} ${yo[2]}'"
    curl -s "http://localhost:8080/taskentries?taskId=${yo[0]}" | jq -r '. | .[] |"at \(.creationTime) minutes \(.durationMinutes)"'
}

IFS=$'\n'
PROJECT_NAME_ID=(${PROJECT_TASK_NAMES_IDS})

for i in "${PROJECT_NAME_ID[@]}"
do
   :
   printTaskEntries $i
done

rm *.tmp