package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
import de.bitrecycling.timeshizz.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ClientService clientService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskEntryService taskEntryService;

    /**
     * This is so ugly, but given the the data approach (only parent ids) seems there is no elegant way to do it.
     * Part of the ugliness comes from me not being able to combine fluxes in the desired way (nested and creating maps).
     * @param id
     * @return
     */
    @GetMapping(value = "/client/{id}", produces = "application/json")
    public ResponseEntity<Report> clientReport(@PathVariable String id){

        Report report = new Report();
        clientService.byId(id).subscribe(report::setClient);
        Map<Project, List<Map.Entry<Task, List<TaskEntry>>>> res = new HashMap<>();
        Map<String, Project> projects = projectService.allByClientId(id).collectMap(project -> project.getId()).block();
        Map<Project, List<Task>> projectTasks = new HashMap<>();
        projects.forEach(
                (s, project) -> projectTasks.put(project,taskService.allByProjectId(s).collectList().block())
        );
        Map<Task, List<TaskEntry>> taskTaskEntries = new HashMap<>();
        projectTasks.forEach((key,tasks)->
                tasks.forEach(task->
                        taskTaskEntries.put(task, taskEntryService.getAllByTaskId(task.getId()).collectList().block())
                )
        );

        projectTasks.forEach((key,tasks)->
                tasks.forEach(task->
                        res.put(key,taskTaskEntries.entrySet()
                                .stream().filter(value->value.getKey().getProjectId().equals(key.getId()))
                                .collect(Collectors.toList()))
                )
        );

        report.setProjects(res);

        return ResponseEntity.ok(report);

    }

}
