package de.bitrecyling.timeshizz.clientlib.project.connector;

import de.bitrecyling.timeshizz.clientlib.project.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * ProjectConnector do not use directly, use the ProjectService to access the Project resource. No plausability or
 * correctness checks here, simply communicates with the web service.
 *
 * created by robo
 */
@Service
public class ProjectConnector {

    @Value("${timeshizz.service.uri}")
    private String baseUrl;

    private String resourceUrl = "/projects";

    private WebClient webClient;

    @PostConstruct
    public void init(){
         webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "de.bitrecycling.timeshizz.clientlib")
                .build();
    }

    public Flux<Project> loadAllProjects() {

        return webClient.get().uri(resourceUrl).exchange().flatMapMany(
                clientResponse -> clientResponse.bodyToFlux(Project.class)
        );
    }

    public Mono<Project> createProject(Project project) {
        return webClient.post().uri(resourceUrl).body(
//                BodyInserters.fromObject(project)
                BodyInserters.fromFormData("name", project.getName())
                        .with("description", project.getDescription())
                        .with("clientId", project.getClientId())
        ).exchange().flatMap(
                clientResponse -> clientResponse.bodyToMono(Project.class));
    }

    public Mono<Void> deleteProject(String id) {
        return webClient.delete().uri(resourceUrl+"/" + id).exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Void.class));
    }
}
