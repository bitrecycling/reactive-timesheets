var timeshizz = new Vue({
    el: '#timeshizz',
    data: {
        clientName: '',
        clientAddress: '',
        projectName: '',
        projectDescription: '',
        projectRate: '',
        taskName: '',
        taskEntryStartTime: '',
        taskEntryDurationMinutes: '',

        showCreateClient: false,
        showEditClient: false,
        showCreateProject: false,
        showEditProject: false,
        showCreateTask: false,
        showEditTask: false,
        showCreateTaskEntry: false,
        showEditTaskEntry: false,

        selectedClient: '',
        selectedProject: '',
        selectedTask: '',
        selectedTaskEntry: '',

        clients: '',
        projects: '',
        tasks: '',

        taskEntries: ''
    },
    methods: {
        createClient: function () {
            insertClient(this.clientName, this.clientAddress);
            this.clientName = '';
            this.clientAddress = '';
        },
        saveClient: function () {
            saveClient(this.selectedClient);
        },
        createProject: function () {
            insertProject(this.projectName, this.projectDescription, this.projectRate, this.selectedClient.id);
            this.projectName = '';
            this.projectDescription = '';
        },
        createTask: function () {
            insertTask(this.taskName, this.selectedProject.id);
            this.taskName ='';
        },
        createTaskEntry: function () {
            insertTaskEntry(this.taskEntryStartTime, this.taskEntryDurationMinutes, this.selectedTask.id);
            this.taskEntryStartTime='';
            this.taskEntryDurationMinutes='';
        },
        clearClient: function(){
            this.clearProject();
            this.showEditClient = false;
            this.showCreateClient = false;
            this.selectedClient='';
        },
        clearProject: function(){
            this.clearTask();
            this.selectedProject='';

        },
        clearTask: function(){
            this.clearTaskEntry();
            this.selectedTask='';

        },
        clearTaskEntry: function(){
            this.selectedTaskEntry='';
        },
        deleteClient:function () {
            deleteClient(this.selectedClient.id);
            this.selectedClient = '';
        },
        clientSelected: function (client) {
            this.selectedClient = client;
            loadProjectsForClient(client.id);
        }
        ,
        projectSelected: function (project) {
            this.selectedProject=project;
            this.showCreateClient = false;
            loadTasksForProject(project.id);
        },
        taskSelected: function (task) {
            this.selectedTask = task;
            loadTaskEntriesForTask(task.id);
        },
        formatTime: function(timeString){
            return prettyPrintIsoDateTime(timeString);
        }

    }
});

function insertClient(clientName, clientAddress) {
    // console.log('post data: name:' + clientName + ' address:' + clientAddress);
    var params = new URLSearchParams();
    params.append('name', clientName);
    params.append('address', clientAddress);
    axios.post('/clients', params).then(function (response) {
        getExistingClients();
    })
        .catch(function (error) {
            console.log(error);
        });
}

function saveClient(client) {
    // console.log('post data: name:' + clientName + ' address:' + clientAddress);
    axios.put('/clients/'+client.id, client).then(function (response) {
        getExistingClients();
    })
        .catch(function (error) {
            console.log(error);
        });
}

function insertProject(projectName, projectDescription, projectRate, clientId) {
    var params = new URLSearchParams();
    params.append('name', projectName);
    params.append('description', projectDescription);
    params.append('rate', projectRate);
    params.append('clientId', clientId);
    axios.post('/projects', params).then(function (response) {
        loadProjectsForClient(clientId);
    })
        .catch(function (error) {
            console.log(error);
        });
}

function insertTask(taskName, projectId) {
    var params = new URLSearchParams();
    params.append('name', taskName);
    params.append('projectId', projectId);
    axios.post('/tasks', params).then(function (response) {
        loadTasksForProject(projectId);
    })
        .catch(function (error) {
            console.log(error);
        });
}

function insertTaskEntry(startTime, durationMinutes, taskId) {
    var params = new URLSearchParams();
    params.append('startTime', startTime);
    params.append('durationMinutes', durationMinutes);
    params.append('taskId', taskId);
    axios.post('/taskentries', params).then(function (response) {
        loadTaskEntriesForTask(taskId);
    })
        .catch(function (error) {
            console.log(error);
        });
}


function deleteClient(clientId){
    axios.delete('/clients/'+clientId).then(function (response) {
        getExistingClients();
    })
        .catch(function (error) {
            console.log(error);
        });

}

function loadProjectsForClient(clientId) {
    axios.get('/projects/?clientId=' + clientId).then(
        function (response) {
            timeshizz.projects = response.data;
        }
    );
}

function loadTasksForProject(projectId) {
    axios.get('/tasks/?projectId=' + projectId).then(
        function (response) {
            timeshizz.tasks = response.data;
        }
    );
}

function loadTaskEntriesForTask(taskId) {
    axios.get('/taskentries/?taskId=' + taskId).then(
        function (response) {
            timeshizz.taskEntries = response.data;
        }
    );
}

function getExistingClients() {
    axios.get('/clients').then(
        function (response) {
            timeshizz.clients = response.data;
        }
    );
}

function prettyPrintIsoDateTime(dateTimeString){
    return moment(dateTimeString).format("YYYY-MM-DD HH:mm ");
}

getExistingClients();
