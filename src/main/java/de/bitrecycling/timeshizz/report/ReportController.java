package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.activity.service.ActivityEntryService;
import de.bitrecycling.timeshizz.activity.service.ActivityService;
import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.NlpTime;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ClientService clientService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityEntryService activityEntryService;

    /**
     * get all taskEntries for given client in given timespan
     *
     * @param id
     * @return
     */
    @GetMapping("/client/{id}")
    public ReportEntity clientReport(@PathVariable("id") String id, @RequestParam("start") String startTimeString, @RequestParam("end") String endTimeString) {
        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString);

        throw new RuntimeException("not implemented");
//        Flux<FullActivityEntry> fullActivityEntryFlux =
//                taskEntryService.getByClientAndByStartTimeBetween(id, startTime, endTime).flatMap(te ->
//                        taskService.byId(te.getActivityId()).flatMap(t ->
//                                projectService.byId(te.getProjectId()).flatMap(p ->
//                                        clientService.byId(id).flatMap(c ->
//                                        Mono.just(new FullActivityEntry(te, t, p, c))
//                                ))));
//
//        ReportEntity report = new ReportEntity();
//        Mono<ReportEntity> reduce = fullActivityEntryFlux
//                .reduce(report, (r, te) ->{
//                            r.setClient(te.getClient());
//                            return r.addActivityEntry(te.getProject(), te.getActivity(), te.getActivityEntry());
//                        }
//                );
//
//        return reduce;
    }
}
