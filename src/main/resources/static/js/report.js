
var timeshizz = new Vue({
    el: '#timeshizz',
    data: {
        clientId: '',
        client: undefined,
        clients: [],
        projectId: '',
        project: undefined,
        projects: [],
        report: '',
        momentjs: moment
    },
    methods: {
        reportForClient: function(clientId){
          return loadProjectsForClient(clientId);
        },

        formatTime: function (timeString) {
            return prettyPrintIsoDateTime(timeString);
        },
        sumActivitysForProject: function (projectId) {
            return _sumActivitysForProject(projectId);
        },
        sumActivityEntriesForActivity: function (taskId) {
            return _sumActivityEntriesForActivity(taskId);
        },
        subtotal: function (project) {
            return _sumActivitysForProject(project.id) * project.rate;

        },
        total: function () {

        },
        startOfMonth: function (cur) {
            if (cur === undefined) {
                cur = new Date();
                cur.setMonth(new Date().getMonth() - 1);
            }
            return getMonthStart(cur);
        },
        endOfMonth: function (cur) {
            if (cur === undefined) {
                cur = new Date();
                cur.setMonth(new Date().getMonth() - 1);
            }
            return getMonthEnd(cur);
        },
        clientSelected: function (client) {
            this.client = client;
            this.clientId = client.id;
            loadProjectsForClient(client.id);
            loadClientReport(client.id);
        }
        ,
        projectSelected: function (project) {
            this.project = project;
            this.projectId = project.id;
        },
        enumerateDays(startDate, endDate) {
            return getDates(startDate, endDate);
        },
        weekend(day) {
            var dayIndex = moment(day).day();
            return dayIndex == 6 || dayIndex == 0;
        },
        sum() {

            var sum = 0;
            this.report.forEach(function (line) {
                sum += line.durationMinutes;
            });
            return sum;
        }

    }
});


function loadClientReport(clientId) {
    // axios.get('/report?clientId=' + clientId+'&start=2000-01-01&end='+'2019-12-31').then(
    axios.get('/report?clientId=' + clientId).then(
        function (response) {
            timeshizz.report = response.data;
            console.log(response.data);
        }
    );
}

function loadProjectReport(projectId) {
    // axios.get('/report?projectId=' + clientId+'&start=2000-01-01&end='+'2019-12-31').then(
    axios.get('/report?projectId=' + clientId).then(
        function (response) {
            timeshizz.report = response.data;
            console.log(response.data);
        }
    );
}


function prettyPrintIsoDateTime(dateTimeString) {
    return moment(dateTimeString).format("YYYY-MM-DD");
}



function init() {
    var params = new URLSearchParams(window.location.search);
    if (params) {
        timeshizz.clientId = params.get("client");
        timeshizz.projectId = params.get("project");
        if(timeshizz.clientId === undefined || timeshizz.clientId == null){
            getExistingClients();
            
        }else {
            if(timeshizz.clientId != null){
                loadClientReport(timeshizz.clientId);
            }
            if(timeshizz.projectId != null){
                loadProjectReport(timeshizz.projectId);
            }
        }
    }
        
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


function getMonthStart(date) {
    return moment(date).startOf('month').toDate();
}

function getMonthEnd(date) {
    return moment(date).endOf('month').toDate();
}


function getDayStart(dayDate){
    var d = new Date(dayDate);
    d.setHours(1, 0, 0, 0);
    return new Date(d).toISOString().split('.')[0];
}

function getDayEnd(dayDate) {
    var d = new Date(dayDate);
    d.setHours(23, 59, 59, 999);
    return new Date(d).toISOString().split('.')[0];
}


// lists all dates between startDate and endDate into Array
function getDates(startDate, endDate) {
    var dates = [],
        currentDate = startDate,
        addDays = function (days) {
            var date = new Date(this.valueOf());
            date.setDate(date.getDate() + days);
            return date;
        };
    while (currentDate <= endDate) {
        dates.push(currentDate);
        currentDate = addDays.call(currentDate, 1);
    }
    return dates;
};


function getExistingClients() {
    axios.get('/clients').then(
        function (response) {
            timeshizz.clients = response.data;
        }
    );
}

function loadProjectsForClient(clientId) {
    axios.get('/projects/?clientId=' + clientId).then(
        function (response) {
            timeshizz.projects = response.data;
        }
    );
}



init();
