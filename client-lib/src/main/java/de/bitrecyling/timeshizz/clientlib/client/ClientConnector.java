package de.bitrecyling.timeshizz.clientlib.client;

import de.bitrecyling.timeshizz.clientlib.client.model.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * created by robo
 */
@Service
public class ClientConnector {

    @Value("${timeshizz.service.uri}")
    String baseUrl;
    private String resourceUrl = "/clients";
    private WebClient webClient;

    @PostConstruct
    public void init(){
         webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "de.bitrecycling.timeshizz.clientlib")
                .build();
    }

    public Flux<Client> loadAllClients() {

        return webClient.get().uri(resourceUrl).exchange().flatMapMany(
                clientResponse -> clientResponse.bodyToFlux(Client.class)
        );
    }

    public Mono<Client> createClient(Client client) {

        return webClient.post().uri(resourceUrl).body(
                BodyInserters.fromFormData("name", client.getName()).with("address", client.getAddress())
        ).exchange().flatMap(
                clientResponse -> clientResponse.bodyToMono(Client.class));
    }

    public Mono<Void> deleteClient(String id) {
        return webClient.delete().uri(resourceUrl +"/" + id).exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Void.class));
    }
}
