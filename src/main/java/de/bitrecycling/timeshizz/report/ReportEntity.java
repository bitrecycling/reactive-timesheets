package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = ReportSerializer.class)
public class ReportEntity {

    private ClientEntity client;
    private Map<ProjectEntity, Map<ActivityEntity, List<ActivityEntryEntity>>> projects = new HashMap<>();

    public ReportEntity() {
    }

    public void addProject(ProjectEntity project) {
        if (!projects.containsKey(project)) {
            projects.put(project, new HashMap<>());
        }
    }

    public void addActivity(ProjectEntity project, ActivityEntity task) {
        addProject(project);
        Map<ActivityEntity, List<ActivityEntryEntity>> tasks = projects.get(project);
        if (tasks != null) {
            if(!projects.get(project).containsKey(task)){
                projects.get(project).put(task, new ArrayList<>());
            }
        }
    }

    public ReportEntity addActivityEntry(ProjectEntity project, ActivityEntity task, ActivityEntryEntity taskEntry) {
        addActivity(project,task);
        projects.get(project).get(task).add(taskEntry);
        return this;
    }

    public void setClient(ClientEntity client){
        this.client = client;
    }
    public ClientEntity getClient(){
        return this.client;
    }


    public Map<ProjectEntity, Map<ActivityEntity, List<ActivityEntryEntity>>> getProjects(){
        return projects;
    }

}
