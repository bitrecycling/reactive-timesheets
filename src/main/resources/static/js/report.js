
var timeshizz = new Vue({
    el: '#timeshizz',
    data: {
        clientId: '',
        projectId: '',
        report: ''
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
        start: function(){
            return getLastMonthStart(new Date());
        },
        end: function(){
            return getLastMonthEnd(new Date());
        }
    }
});


function loadClientReport(clientId) {
    axios.get('/report/client/' + clientId+'?start=2000-01-01&end='+'2019-12-31').then(
        function (response) {
            timeshizz.report = response.data;
            console.log(response.data);
        }
    );
}


function prettyPrintIsoDateTime(dateTimeString) {
    return moment(dateTimeString).format("YYYY-MM-DD HH:mm ");
}



function init() {
    var params = new URLSearchParams(window.location.search);
    if (params) {
        timeshizz.clientId = params.get("client");
        timeshizz.projectId = params.get("project");
    }
    loadClientReport(timeshizz.clientId);
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
