package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = ReportSerializer.class)
public class Report {

    private Client client;
    private Map<Project, Map<Task, List<TaskEntry>>> projects = new HashMap<>();

    public Report() {
    }

    public void addProject(Project project) {
        if (!projects.containsKey(project)) {
            projects.put(project, new HashMap<>());
        }
    }

    public void addTask(Project project, Task task) {
        addProject(project);
        Map<Task, List<TaskEntry>> tasks = projects.get(project);
        if (tasks != null) {
            if(!projects.get(project).containsKey(task)){
                projects.get(project).put(task, new ArrayList<>());
            }
        }
    }

    public Report addTaskEntry(Project project, Task task, TaskEntry taskEntry) {
        addTask(project,task);
        projects.get(project).get(task).add(taskEntry);
        return this;
    }

    public void setClient(Client client){
        this.client = client;
    }
    public Client getClient(){
        return this.client;
    }


    public Map<Project, Map<Task, List<TaskEntry>>> getProjects(){
        return projects;
    }

}
