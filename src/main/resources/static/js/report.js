
var timeshizz = new Vue({
    el: '#timeshizz',
    data: {
        clientId: '',
        projectId: '',

        client: '',
        projects: {},
        tasks: {},
        taskEntries: {},


    },
    methods: {
        formatTime: function (timeString) {
            return prettyPrintIsoDateTime(timeString);
        },
        sumTasksForProject: function (projectId) {
            return _sumTasksForProject(projectId);
        },
        sumTaskEntriesForTask: function (taskId) {
            return _sumTaskEntriesForTask(taskId);
        },
        subtotal: function (project) {
           return _sumTasksForProject(project.id) * project.rate;

        },
        total: function () {

        },
        start: function(){
            return getLastMonthStart(new Date());
        },
        end: function(){
            return getLastMonthEnd(new Date());
        }
    }
});


function loadClient(clientId) {
    axios.get('/clients/' + clientId).then(
        function (response) {
            if (timeshizz.projectId) {
                loadProject(timeshizz.projectId, clientId);
            } else {
                loadProjectsForClient(timeshizz.clientId);
            }
            timeshizz.client = response.data;
        }
    );
}

function loadProject(projectId, clientId) {
    axios.get('/projects/' + projectId).then(
        function (response) {
            Vue.set(timeshizz.projects, clientId, [response.data]);
            loadTasksForProject(projectId);
        }
    );
}


function loadProjectsForClient(clientId) {
    axios.get('/projects/?clientId=' + clientId).then(
        function (response) {
            Vue.set(timeshizz.projects, clientId, response.data);
            response.data.forEach(function (project) {
                loadTasksForProject(project.id);
            });
        }
    );
}

function loadTasksForProject(projectId) {
    axios.get('/tasks/?projectId=' + projectId).then(
        function (response) {
            Vue.set(timeshizz.tasks, projectId, response.data);
            response.data.forEach(function (task) {
                loadTaskEntriesForTask(task, projectId);
            });
        }
    );
}

function loadTaskEntriesForTask(task, projectId) {
    axios.get('/taskentries/?start='+getLastMonthStart(new Date())+'&until='+getLastMonthEnd(new Date())+'&taskId=' + task.id).then(
        function (response) {
            if(response.data.length > 0){
                Vue.set(timeshizz.taskEntries, task.id, response.data);
            }

        }
    );
}

function prettyPrintIsoDateTime(dateTimeString) {
    return moment(dateTimeString).format("YYYY-MM-DD HH:mm ");
}


function _sumTasksForProject(projectId) {
    var sum = 0;
    if (timeshizz.tasks[projectId]) {
        timeshizz.tasks[projectId].forEach(function (task) {
            sum += _sumTaskEntriesForTask(task.id);
        });
    }
    return sum;
}

function _sumTaskEntriesForTask(taskId) {
    var sum = 0;
    if (timeshizz.taskEntries[taskId]) {
        timeshizz.taskEntries[taskId].forEach(function (taskEntry) {
            sum += taskEntry.durationMinutes;
        });
    }
    return sum;
}

function getTaskEntriesForSingleDay(dayDate){
    axios.get("/taskentries/?start="+getDayStart(dayDate)+"&until="+getDayEnd(dayDate)).then(function(response){
        //TODO
    });
}

function init() {
    var params = new URLSearchParams(window.location.search);
    if (params) {
        timeshizz.clientId = params.get("client");
        timeshizz.projectId = params.get("project");
        loadClient(timeshizz.clientId);
    }
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

function getDayStart(dayDate){
    var d = new Date(dayDate);
    d.setHours(1,0,0,0);
    return new Date(d).toISOString().split('.')[0] ;
}
function getDayEnd(dayDate){
    var d = new Date(dayDate);
    d.setHours(23,59,59,999);
    return new Date(d).toISOString().split('.')[0] ;
}

function getSingleDaysBetween(startDate, endDate) {
    var singleDays = [],
        currentDay = startDate,
        addDays = function(days) {
            var date = new Date(this.valueOf());
            date.setDate(date.getDate() + days);
            return date;
        };
    while (currentDay <= endDate) {
        singleDays.push(currentDay);
        currentDay = addDays.call(currentDay, 1);
    }
    return singleDays;
};

init();
