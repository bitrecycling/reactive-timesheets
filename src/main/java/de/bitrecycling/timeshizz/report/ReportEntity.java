package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = ReportSerializer.class)
public class ReportEntity {

    private ClientEntity client;
    private Map<ProjectEntity, Map<TaskEntity, List<TaskEntryEntity>>> projects = new HashMap<>();

    public ReportEntity() {
    }

    public void addProject(ProjectEntity project) {
        if (!projects.containsKey(project)) {
            projects.put(project, new HashMap<>());
        }
    }

    public void addTask(ProjectEntity project, TaskEntity task) {
        addProject(project);
        Map<TaskEntity, List<TaskEntryEntity>> tasks = projects.get(project);
        if (tasks != null) {
            if(!projects.get(project).containsKey(task)){
                projects.get(project).put(task, new ArrayList<>());
            }
        }
    }

    public ReportEntity addTaskEntry(ProjectEntity project, TaskEntity task, TaskEntryEntity taskEntry) {
        addTask(project,task);
        projects.get(project).get(task).add(taskEntry);
        return this;
    }

    public void setClient(ClientEntity client){
        this.client = client;
    }
    public ClientEntity getClient(){
        return this.client;
    }


    public Map<ProjectEntity, Map<TaskEntity, List<TaskEntryEntity>>> getProjects(){
        return projects;
    }

}
