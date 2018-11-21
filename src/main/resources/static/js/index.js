var dashboard = new Vue({
    el: '#dashboard',
    data: {
        recent: {
            taskEntries: {},
            tasks: {},
            projects: {},
            clients: []
        },
        lastweek: {
            taskEntries: {},
            tasks: {},
            projects: {},
            clients: []
        },
        lastmonth: {
            taskEntries: {},
            tasks: {},
            projects: {},
            clients: []
        }
    },
    methods: {
        sumProject: function(tasks, target){
            var that = this;
            var sum = 0;
            tasks.forEach(function(task){
                var taskEntries = target.taskEntries[task.id];
                taskEntries.forEach(function (taskentry) {
                    sum += taskentry.durationMinutes;
                });

            });
            return sum;
        }
    }
});


function loadClientForProject(clientId, target) {
    axios.get('/clients/' + clientId).then(function (response) {
        var client = response.data;
        target.clients.push(client);
    });
}


function loadProjectForTask(projectId, target) {
    axios.get('/projects/' + projectId).then(function (response) {
        var project = response.data;
        var tmp = target.projects[project.clientId];
        if (tmp) {
            tmp.push(project);
        } else {
            target.projects[project.clientId] = [project];
            loadClientForProject(project.clientId, target);
        }
    });
}

function loadTaskForTaskEntry(taskId, target) {
    axios.get('/tasks/' + taskId).then(function (response) {
        var task = response.data;
        var tmp = target.tasks[task.projectId];
        if (tmp) {
            tmp.push(task);
        } else {
            target.tasks[task.projectId] = [task];
            loadProjectForTask(task.projectId, target);
        }
    });
}

function getTaskEntriesBetween(fromDate, toDate, target) {
    var url = '/taskentries?start='+fromDate+'&until='+toDate;
    axios.get(url).then(
        function (response) {
            response.data.forEach(function (taskEntry) {
                var tmp = target.taskEntries[taskEntry.taskId];
                if (tmp) {
                    tmp.push(taskEntry);
                } else {
                    target.taskEntries[taskEntry.taskId] = [taskEntry];
                    loadTaskForTaskEntry(taskEntry.taskId, target);
                }
            });

        }
    );
}

function getMostRecentTaskEntries(count, target) {
    var url = '/taskentries';
    if(count){
        url+='?count=' + count;
    }
    axios.get(url).then(
        function (response) {
            response.data.forEach(function (taskEntry) {
                var tmp = target.taskEntries[taskEntry.taskId];
                if (tmp) {
                    tmp.push(taskEntry);
                } else {
                    target.taskEntries[taskEntry.taskId] = [taskEntry];
                    loadTaskForTaskEntry(taskEntry.taskId, target);
                }
            });

        }
    );
}

function getLastWeekStart(date){
    var pff = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 1);
    var d= new Date(date.setDate(pff));
    d.setHours(1,0,0,0);
    var x= d.setDate(d.getDate()-6);
    return new Date(x).toISOString().split('.')[0] ;

}

function getLastWeekEnd(date){
    var pff = date.getDate() - date.getDay() + (date.getDay() === 0 ? +6 : 6);
    var d= new Date(date.setDate(pff));
    d.setHours(1,0,0,0);
    var x= d.setDate(d.getDate()-6);
    return new Date(x).toISOString().split('.')[0] ;
}

function getLastMonthStart(date){
    var d = new Date(new Date(date.setDate(0)).setDate(1));
    d.setHours(2,0,0,0);
    return new Date(d).toISOString().split('.')[0] ;
}

function getLastMonthEnd(date){
    var d = new Date(new Date(date.setDate(0)));
    d.setHours(1,0,0,0);
    return new Date(d).toISOString().split('.')[0] ;
}



// getExistingClients();
getMostRecentTaskEntries(20,dashboard.recent);
getTaskEntriesBetween(getLastWeekStart(new Date()), getLastWeekEnd(new Date()), dashboard.lastweek);
getTaskEntriesBetween(getLastMonthStart(new Date()), getLastMonthEnd(new Date()), dashboard.lastmonth);