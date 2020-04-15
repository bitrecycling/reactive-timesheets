package de.bitrecycling.timeshizz.activity.model;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
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
    private ProjectEntity project;
    @NonNull
    private ClientEntity client;
}
