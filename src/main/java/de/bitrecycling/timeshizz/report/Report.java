package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Report {

    private Client client;
    private Map<Project, List<Map.Entry<Task, List<TaskEntry>>>> projects = new HashMap<>();

}
