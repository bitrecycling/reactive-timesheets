package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.activity.service.ActivityEntryService;
import de.bitrecycling.timeshizz.activity.service.ActivityService;
import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.NlpTime;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
     * get all activity entries for given client in given timespan
     *
     * @param clientId
     * @return
     */
    @GetMapping(params = {"clientId"})
    public List<ActivityEntryEntity> clientReport(@RequestParam("clientId") UUID clientId, @RequestParam(value = "start",required = false) Optional<String> startTimeString, @RequestParam(value = "end", required = false) Optional<String> endTimeString) {

        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);
        return activityEntryService.getForClientByStartTimeBetween(clientId, startTime, endTime);
    }

    /**
     *       get all activity entries for given client in given timespan
     * 
     * @param projectId
     * @param startTimeString
     * @param endTimeString
     * @return
     */
    @GetMapping(params = {"projectId"})
    public List<ActivityEntryEntity> projectReport(@RequestParam("projectId") UUID projectId, @RequestParam(value = "start", required = false) Optional<String> startTimeString, @RequestParam(value = "end", required = false) Optional<String> endTimeString) {
        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);
        return activityEntryService.getForProjectByStartTimeBetween(projectId, startTime, endTime);
    }
}
