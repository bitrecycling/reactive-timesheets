var timeshizz = new Vue({
    el: '#timeshizz',
    data: {
        clientName: '',
        clientAddress: '',
        projectName: '',
        projectDescription: '',
        projectRate: '',
        activityName: '',
        activityEntryStartTime: '',
        activityEntryDurationMinutes: '',

        showCreateClient: false,
        showEditClient: false,
        showCreateProject: false,
        showEditProject: false,
        showCreateActivity: false,
        showEditActivity: false,
        showCreateActivityEntry: false,
        showEditActivityEntry: false,

        selectedClient: '',
        selectedProject: '',
        selectedActivity: '',
        selectedActivityEntry: '',

        clientId: '',
        projectId: '',
        activityId: '',

        clients: '',
        projects: '',
        activities: '',
        activityEntries: ''
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
            this.projectRate = '';
        },
        saveProject: function () {
            saveProject(this.selectedProject);
        },
        
        createActivity: function () {
            insertActivity(this.activityName, this.selectedProject.id);
            this.activityName ='';
        },
        createActivityEntry: function () {
            insertActivityEntry(this.activityEntryStartTime, this.activityEntryDurationMinutes, this.selectedActivity.id);
            this.activityEntryStartTime='';
            this.activityEntryDurationMinutes='';
        },
        clearClient: function(){
            this.clearProject();
            this.showEditClient = false;
            this.showCreateClient = false;
            this.selectedClient='';
            this.clientId='';
            loadProjectsForClient(this.selectedClient.id);
        },
        clearProject: function(){
            this.clearActivity();
            this.showEditProject = false;
            this.showCreateProject = false;
            this.selectedProject='';
            this.projectId='';
        },
        clearActivity: function(){
            this.clearActivityEntry();
            this.selectedActivity='';
            this.activityId='';

        },
        clearActivityEntry: function(){
            this.selectedActivityEntry='';
        },
        deleteClient:function () {
            deleteClient(this.selectedClient.id);
            this.clearClient();
        },
        deleteProject:function () {
            deleteProject(this.selectedProject.id);
            this.clearProject();
        },
        deleteActivity:function () {
            deleteActivity(this.selectedActivity.id);
            this.clearActivity();
        },
        deleteActivityEntry:function () {
            deleteClient(this.selectedActivityEntry.id);
            this.clearActivityEntry();
        },
        clientSelected: function (client) {
            this.selectedClient = client;
            loadProjectsForClient(client.id);
        }
        ,
        projectSelected: function (project) {
            this.selectedProject=project;
            this.showCreateClient = false;
            loadActivitiesForProject(project.id);
        },
        activitySelected: function (activity) {
            this.selectedActivity = activity;
            loadActivityEntriesForActivity(activity.id);
        },
        formatTime: function(timeString){
            return prettyPrintIsoDateTime(timeString);
        }
    }
});



function insertClient(clientName, clientAddress) {
    // console.log('post data: name:' + clientName + ' address:' + clientAddress);
    let client = {name:clientName,address:clientAddress};
    axios.post('/clients', client).then(function (response) {
        getExistingClients();
    })
        .catch(function (error) {
            console.log(error);
        });
}

function saveClient(client) {
    axios.put('/clients/'+client.id, client).then(function (response) {
        getExistingClients();
    })
        .catch(function (error) {
            console.log(error);
        });
}

function saveProject(project) {
    axios.put('/projects/'+project.id, project).then(function (response) {
        loadProjectsForClient(project.clientId);
    })
        .catch(function (error) {
            console.log(error);
        });
}


function insertProject(projectName, projectDescription, projectRate, clientId) {
    let project = {name:projectName,description:projectDescription,rate:projectRate,clientId:clientId};
    // var params = new URLSearchParams();
    // params.append('name', projectName);
    // params.append('description', projectDescription);
    // params.append('rate', projectRate);
    // params.append('clientId', clientId);
    axios.post('/projects', project).then(function (response) {
        loadProjectsForClient(clientId);
    })
        .catch(function (error) {
            console.log(error);
        });
}

function insertActivity(activityName, projectId) {
    let acticvity = {name:activityName, projectId:projectId}
    axios.post('/activities', acticvity).then(function (response) {
        loadActivitiesForProject(projectId);
    })
        .catch(function (error) {
            console.log(error);
        });
}

function insertActivityEntry(startTime, durationMinutes, activityId) {
    let activityEntry = {startTime:startTime,durationMinutes:durationMinutes,activityId:activityId}
    axios.post('/activityentries', activityEntry).then(function (response) {
        loadActivityEntriesForActivity(activityId);
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

function deleteProject(projectId){
    axios.delete('/projects/'+projectId).then(function (response) {
        loadProjectsForClient(timeshizz.selectedClient.id);
    })
        .catch(function (error) {
            console.log(error);
        });

}

function deleteActivity(activityId){
    axios.delete('/activities/'+activityId).then(function (response) {
        getExistingActivities();
    })
        .catch(function (error) {
            console.log(error);
        });

}
function deleteActivityEntry(activityEntryId){
    axios.delete('/activityentries/'+activityEntryId).then(function (response) {
        getExistingActivityEntries();
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

function loadActivitiesForProject(projectId) {
    axios.get('/activities/?projectId=' + projectId).then(
        function (response) {
            timeshizz.activities = response.data;
        }
    );
}

function loadActivityEntriesForActivity(activityId) {
    axios.get('/activityentries/?activityId=' + activityId).then(
        function (response) {
            timeshizz.activityEntries = response.data;
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

function getExistingProjects() {
    axios.get('/projects').then(
        function (response) {
            timeshizz.projects = response.data;
        }
    );
}

function getExistingActivities() {
    axios.get('/activities').then(
        function (response) {
            timeshizz.activities = response.data;
        }
    );
}

function getExistingActivityEntries() {
    axios.get('/activityentries').then(
        function (response) {
            timeshizz.activityEntries = response.data;
        }
    );
}

function prettyPrintIsoDateTime(dateTimeString){
    return moment(dateTimeString).format("YYYY-MM-DD HH:mm ");
}

function init(){
    var params = new URLSearchParams(window.location.search);
    if(params){
        timeshizz.clientId= params.get("client");
        timeshizz.projectId = params.get("project");
        timeshizz.activityId = params.get("activity");
    }
}

getExistingClients();
init();
