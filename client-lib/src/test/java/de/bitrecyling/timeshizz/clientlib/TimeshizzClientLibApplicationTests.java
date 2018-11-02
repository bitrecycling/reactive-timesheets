package de.bitrecyling.timeshizz.clientlib;

import de.bitrecyling.timeshizz.clientlib.client.ClientConnector;
import de.bitrecyling.timeshizz.clientlib.client.model.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;


/**
 * tests (parts of) the client lib
 * <p>
 * created by robo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeshizzClientLibApplicationTests {

    @Autowired
    ClientConnector clientConnector;

    @Test
    public void contextLoads() {
    }


    @Test
    public void testClients() {
        Client test = new Client();
        test.setName("xyzClientName");
        test.setAddress("xyzClientAddress");

        StepVerifier.create(clientConnector.createClient(test))
                .expectNextMatches(
                        client -> {
                            test.setId(client.getId());
                            return test.equals(client);
                        }
                ).verifyComplete();

        StepVerifier.create(clientConnector.loadAllClients().next()).expectNextMatches(
                client -> test.equals(client)
        ).verifyComplete();

        StepVerifier.create(
                clientConnector.deleteClient(test.getId()
                )).expectNext().verifyComplete();
    }

}
