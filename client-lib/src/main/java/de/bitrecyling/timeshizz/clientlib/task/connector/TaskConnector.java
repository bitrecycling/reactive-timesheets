package de.bitrecyling.timeshizz.clientlib.task.connector;

import de.bitrecyling.timeshizz.clientlib.task.model.DurationTaskEntry;
import de.bitrecyling.timeshizz.clientlib.task.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * TaskConnector do not use directly, use the ProjectService to access the Task resource. No plausability or
 * correctness checks here, simply communicates with the web service.
 * <p>
 * created by robo
 */
@Service
public class TaskConnector {

    @Value("${timeshizz.service.uri}")
    private String baseUrl;

    private String taskUrl = "/tasks";
    private String taskEntryUrl = "/taskentries";

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "de.bitrecycling.timeshizz.clientlib")
                .build();
    }

    public Flux<Task> loadAllTasks() {

        return webClient.get().uri(taskUrl).exchange().flatMapMany(
                clientResponse -> clientResponse.bodyToFlux(Task.class)
        );
    }

    public Flux<Task> loadAllTasksForProject(String projectId) {
        return webClient.get().uri(String.format(taskUrl + "/?projectId=%s", projectId)).exchange().flatMapMany(
                clientResponse -> clientResponse.bodyToFlux(Task.class)
        );
    }

    /**
     * create task with name and projectId
     *
     * @param task
     * @return
     */
    public Mono<Task> createTask(Task task) {
        return webClient.post().uri(taskUrl).body(
                BodyInserters.fromFormData("name", task.getName()).with("projectId", task.getProjectId())
        ).exchange().flatMap(
                clientResponse -> clientResponse.bodyToMono(Task.class));
    }

    public Mono<DurationTaskEntry> createTaskEntry(DurationTaskEntry taskEntry) {
        return webClient.post().uri(taskEntryUrl).body(
                BodyInserters.fromFormData(
                        "durationMinutes", taskEntry.getDurationMinutes().toString())
                        .with("taskId", taskEntry.getTaskId()
                        )
        ).exchange().flatMap(
                clientResponse -> clientResponse.bodyToMono(DurationTaskEntry.class)
        );
    }

    public Mono<Void> deleteTask(String id) {
        return webClient.delete().uri(taskUrl + "/" + id).exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Void.class));
    }


    public Mono<Void> deleteTaskEntry(String id) {
        return webClient.delete().uri(taskEntryUrl+"/"+id).exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Void.class));
    }
}
