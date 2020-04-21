package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.activity.service.ActivityEntryService;
import de.bitrecycling.timeshizz.activity.service.ActivityService;
import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.NlpTime;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
     * @param clientId
     * @return
     */
    @GetMapping(params = {"clientId"})
    public ReportJson clientReport(@PathVariable("clientId") UUID clientId, @RequestParam("start") String startTimeString, @RequestParam("end") String endTimeString) {
        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString);
        final List<ProjectEntity> projectEntities = projectService.allByClientId(clientId);
        return null;
    }
}
