package de.bitrecycling.timeshizz.report;

import de.bitrecycling.timeshizz.common.NlpTime;
import de.bitrecycling.timeshizz.management.service.ClientService;
import de.bitrecycling.timeshizz.management.service.ProjectService;
import de.bitrecycling.timeshizz.timetracking.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.timetracking.service.ActivityEntryService;
import de.bitrecycling.timeshizz.timetracking.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ClientService clientService;
    private final ProjectService projectService;
    private final ActivityService activityService;
    private final ActivityEntryService activityEntryService;

    /**
     * get all activity entries for given client in given timespan
     *
     * @param clientId
     * @return
     */
    @GetMapping(params = {"clientId"})
    public List<ActivityEntryEntity> clientReport(
            @RequestParam("clientId") UUID clientId,
            @RequestParam(value = "start", required = false) Optional<String> startTimeString,
            @RequestParam(value = "end", required = false) Optional<String> endTimeString) {

        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);
        return activityEntryService.getForClientByStartTimeBetween(clientId, startTime, endTime);

    }

    /**
     * get all activity entries for given client in given timespan
     *
     * @param clientId
     * @return
     */
    @GetMapping(params = {"clientId", "grouping"})
    public Map<String, List<ActivityEntryEntity>> groupedClientReport(
            @RequestParam("clientId") UUID clientId,
            @RequestParam(value = "start", required = false) Optional<String> startTimeString,
            @RequestParam(value = "end", required = false) Optional<String> endTimeString,
            @RequestParam(value = "grouping") String groupingString) {

        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);
        
        return activityEntryService.getGroupedForClientByStartTimeBetween(clientId, startTime, endTime, groupingString);

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
    public List<ActivityEntryEntity> projectReport(
            @RequestParam("projectId") UUID projectId,
            @RequestParam(value = "start", required = false) Optional<String> startTimeString,
            @RequestParam(value = "end", required = false) Optional<String> endTimeString) {

        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);

        return activityEntryService.getForProjectByStartTimeBetween(projectId, startTime, endTime);
    }

    /**
     *       get all activity entries for given client in given timespan
     *
     * @param projectId
     * @param startTimeString
     * @param endTimeString
     * @return
     */
    @GetMapping(params = {"projectId", "grouping"})
    public Map<String, List<ActivityEntryEntity>>  groupedProjectReport(
            @RequestParam("projectId") UUID projectId,
            @RequestParam(value = "start", required = false) Optional<String> startTimeString,
            @RequestParam(value = "end", required = false) Optional<String> endTimeString,
            @RequestParam(value = "grouping", required = false) String groupingString) {
        LocalDateTime startTime = NlpTime.parseTimeFromNlpString(startTimeString.orElse(null)).orElse(null);
        LocalDateTime endTime = NlpTime.parseTimeFromNlpString(endTimeString.orElse(null)).orElse(null);

        return activityEntryService.getGroupedForProjectByStartTimeBetween(projectId, startTime, endTime, groupingString);
    }
}
