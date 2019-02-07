package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.controller.ControllerUtils;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import de.bitrecycling.timeshizz.task.model.FullTaskEntry;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
import de.bitrecycling.timeshizz.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
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
     * get all taskEntries for given client in given timespan
     *
     * @param id
     * @return
     */
    @GetMapping("/client/{id}")
    public Mono<Report> clientReport(@PathVariable("id") String id, @RequestParam("start") String startTimeString, @RequestParam("end") String endTimeString) {
        LocalDateTime startTime = ControllerUtils.parseTime(startTimeString);
        LocalDateTime endTime = ControllerUtils.parseTime(endTimeString);

        Flux<FullTaskEntry> fullTaskEntryFlux =
                taskEntryService.getByClientAndByStartTimeBetween(id, startTime, endTime).flatMap(te ->
                        taskService.byId(te.getTaskId()).flatMap(t ->
                                projectService.byId(te.getProjectId()).flatMap(p ->
                                        clientService.byId(id).flatMap(c ->
                                        Mono.just(new FullTaskEntry(te, t, p, c))
                                ))));

        Report report = new Report();
        Mono<Report> reduce = fullTaskEntryFlux
                .reduce(report, (r, te) ->{
                            r.setClient(te.getClient());
                            return r.addTaskEntry(te.getProject(), te.getTask(), te.getTaskEntry());
                        }
                );

        return reduce;
    }
}
