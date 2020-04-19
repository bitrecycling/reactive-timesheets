var dashboard = new Vue({
    el: '#dashboard',
    data: {
        recent: [],
        projects: []
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
        getMostRecent: function (count) {
            getMostRecentActivityEntries(count, dashboard.recent)
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
                //process data "inside out"
                
               dashboard.recent.push(activityEntry); 
            });
        }
    );
}


function getProjects(count, target) {
    var url = '/projects';
    
    axios.get(url).then(
        function (response) {
            response.data.forEach(function (project){
                dashboard.projects.push(project);    
            });
        }
    );
}


getMostRecentActivityEntries(20,dashboard.recent);
getProjects(dashboard.projects);