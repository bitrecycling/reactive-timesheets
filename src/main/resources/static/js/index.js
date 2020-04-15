var dashboard = new Vue({
    el: '#dashboard',
    data: {
        recent: {
            activityEntries: {},
            activities: {},
            projects: {},
            clients: []
        },
        lastweek: {
            activityEntries: {},
            activities: {},
            projects: {},
            clients: []
        },
        lastmonth: {
            activityEntries: {},
            activities: {},
            projects: {},
            clients: []
        }
    },
    methods: {
        sumProject: function(activities, target){
            var that = this;
            var sum = 0;
            activities.forEach(function(activity){
                var activityEntries = target.activityEntries[activity.id];
                activityEntries.forEach(function (activityentry) {
                    sum += activityentry.durationMinutes;
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


function loadProjectForActivity(projectId, target) {
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

function loadActivityForActivityEntry(activityId, target) {
    axios.get('/activities/' + activityId).then(function (response) {
        var activity = response.data;
        var tmp = target.activities[activity.projectId];
        if (tmp) {
            tmp.push(activity);
        } else {
            target.activities[activity.projectId] = [activity];
            loadProjectForActivity(activity.projectId, target);
        }
    });
}

function getActivityEntriesBetween(fromDate, toDate, target) {
    var url = '/activityentries?start='+fromDate+'&until='+toDate;
    axios.get(url).then(
        function (response) {
            response.data.forEach(function (activityEntry) {
                var tmp = target.activityEntries[activityEntry.activityId];
                if (tmp) {
                    tmp.push(activityEntry);
                } else {
                    target.activityEntries[activityEntry.activityId] = [activityEntry];
                    loadActivityForActivityEntry(activityEntry.activityId, target);
                }
            });

        }
    );
}

function getMostRecentActivityEntries(count, target) {
    var url = '/activityentries';
    if(count){
        url+='?count=' + count;
    }
    axios.get(url).then(
        function (response) {
            response.data.forEach(function (activityEntry) {
                var tmp = target.activityEntries[activityEntry.activityId];
                if (tmp) {
                    tmp.push(activityEntry);
                } else {
                    target.activityEntries[activityEntry.activityId] = [activityEntry];
                    loadActivityForActivityEntry(activityEntry.activityId, target);
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
getMostRecentActivityEntries(20,dashboard.recent);
getActivityEntriesBetween(getLastWeekStart(new Date()), getLastWeekEnd(new Date()), dashboard.lastweek);
getActivityEntriesBetween(getLastMonthStart(new Date()), getLastMonthEnd(new Date()), dashboard.lastmonth);