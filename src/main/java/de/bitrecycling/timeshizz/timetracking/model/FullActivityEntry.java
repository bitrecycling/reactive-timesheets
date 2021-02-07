package de.bitrecycling.timeshizz.timetracking.model;

import de.bitrecycling.timeshizz.management.model.Client;
import de.bitrecycling.timeshizz.management.model.Project;
import lombok.Data;
import lombok.NonNull;


/**
 * The full taskentry model. it contains also the project and client objects instead of just their ids.
 * <p>
 * by robo
 */

@Data
public class FullActivityEntry {
    @NonNull
    private ActivityEntryEntity activityEntry;
    @NonNull
    private ActivityEntity activity;
    @NonNull
    private Project project;
    @NonNull
    private Client client;
}
