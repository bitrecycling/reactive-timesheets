var dashboard = new Vue({
    el: '#dashboard',
    data: {
        clients:'',
        recentTasks:[],
    },
})


function getExistingClients() {
    axios.get('/clients').then(
        function (response) {
            dashboard.clients = response.data;
        }
    );
}

function getTaskForTaskEntry(taskEntry){

}

function loadTasksForTaskEntries(taskentries){
    taskentries.forEach(function(taskentry){
        axios.get('/tasks/'+taskentry.taskId).then(function(response){
                dashboard.recentTasks.push(response.data.name)
            }
        );
    })
}


function getRecentActivities() {
    axios.get('/taskentries').then(
        function (response) {
            loadTasksForTaskEntries(response.data);
        }
    );
}

getExistingClients();
getRecentActivities();