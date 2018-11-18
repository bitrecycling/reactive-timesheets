var dashboard = new Vue({
    el: '#dashboard',
    data: {
        clients: '',
        recentTaskEntries: {},
        recentTasks: {},
        recentProjects: {},
        recentClients: []
    }
});


function getExistingClients() {
    axios.get('/clients').then(
        function (response) {
            dashboard.clients = response.data;
        }
    );
}

function loadClientForProject(clientId) {
    axios.get('/clients/' + clientId).then(function (response) {
        var client = response.data;
        dashboard.recentClients.push(client);
    });
}


function loadProjectForTask(projectId) {
    axios.get('/projects/' + projectId).then(function (response) {
        var project = response.data;
        var tmp = dashboard.recentProjects[project.clientId];
        if (tmp) {
            tmp.push(project);
        } else {
            dashboard.recentProjects[project.clientId] = [project];
            loadClientForProject(project.clientId);
        }
    });
}

function loadTaskForTaskEntry(taskId) {
    axios.get('/tasks/' + taskId).then(function (response) {
        var task = response.data;
        var tmp = dashboard.recentTasks[task.projectId];
        if (tmp) {
            tmp.push(task);
        } else {
            dashboard.recentTasks[task.projectId]=[task];
            loadProjectForTask(task.projectId);
        }
    });
}

function getRecentTaskEntries(count) {
    axios.get('/taskentries?count=' + count).then(
        function (response) {
            response.data.forEach(function (taskEntry) {
                var tmp = dashboard.recentTaskEntries[taskEntry.taskId];
                if (tmp) {
                    tmp.push(taskEntry);
                } else {
                    dashboard.recentTaskEntries[taskEntry.taskId]=[taskEntry];
                    loadTaskForTaskEntry(taskEntry.taskId);
                }
            });

        }
    );
}


// getExistingClients();
getRecentTaskEntries(20);