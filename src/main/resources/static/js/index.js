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
        sumProject: function(tasks){
            var that = this;
            var sum = 0;
            tasks.forEach(function(task){
                var taskEntries = that.recent.taskEntries[task.id];
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

function getTaskEntries(count, target) {
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


// getExistingClients();
getTaskEntries(20,dashboard.recent);