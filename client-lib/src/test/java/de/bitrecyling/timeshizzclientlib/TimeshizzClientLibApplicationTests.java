package de.bitrecyling.timeshizzclientlib;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;


/**
 * tests (parts of) the client lib
 *
 * created by robo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeshizzClientLibApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
    public void testConnect(){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 de.bitrecycling.timeshizz.WebClientMain")
                .build();

        StepVerifier.create(webClient.get().uri("/projects").exchange())
                .assertNext(
                        clientResponse -> Assert.assertTrue(clientResponse.statusCode().is2xxSuccessful())
                ).verifyComplete();
    }

}
