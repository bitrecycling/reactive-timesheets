var dashboard = new Vue({
    el: '#dashboard',
    data: {
        activityCount:10,
        recent: [],
        clients: [],
        clientsAndProjects: {}
    },
    methods: {
        sumProject: function (activities, target) {
            var that = this;
            var sum = 0;
            activities.forEach(function (activity) {
                var activityEntries = target.activityEntries[activity.id];
                activityEntries.forEach(function (activityentry) {
                    sum += activityentry.durationMinutes;
                });

            });
            return sum;
        },
        getMostRecent: function () {
            dashboard.recent = [];
            getMostRecentActivityEntries(dashboard.activityCount, dashboard.recent)
        },
        prettyPrintTime: function (dateTimeString) {
            return moment(dateTimeString).format("YYYY-MM-DD HH:mm ");
        },
        track: function(activityEntry){
            window.location = "track.html?client="+activityEntry.activity.project.client.id+"&project="+activityEntry.activity.project.id+"&activity="+activityEntry.activity.id;
        }
    }
});

function getMostRecentActivityEntries(count, target) {
    var url = '/activityentries';
    if(count){
        url+='?count=' + count;
        url+='&details=true';
    }
    axios.get(url).then(
        function (response) {
            response.data.forEach(function(activityEntry){
               dashboard.recent.push(activityEntry); 
            });
        }
    );
}


function getAllProjectsAndMapToClients() {
    var url = '/projects';
    dashboard.clients.forEach(function(client) {
        axios.get(url + "?clientId=" + client.id).then(
            function (response) {
                response.data.forEach(function (project) {

                    if (client.projects === undefined) {
                        client.projects = [];
                    }
                    client.projects.push(project);
                });
            });
    });
}

function getAllClientsProjects() {
    var url = '/clients';
    dashboard.clients = [];
    axios.get(url).then(
        function (response) {
      
            response.data.forEach(function (client){
                dashboard.clients.push(client);
            });
            getAllProjectsAndMapToClients();
        }
    );
}


getMostRecentActivityEntries(dashboard.activityCount,dashboard.recent);
getAllClientsProjects();