package de.bitrecycling.timeshizz.clientlib;

import de.bitrecycling.timeshizz.clientlib.client.connector.ClientConnector;
import de.bitrecycling.timeshizz.clientlib.client.model.Client;
import de.bitrecycling.timeshizz.clientlib.project.connector.ProjectConnector;
import de.bitrecycling.timeshizz.clientlib.project.model.Project;
import de.bitrecycling.timeshizz.clientlib.task.connector.TaskConnector;
import de.bitrecycling.timeshizz.clientlib.task.model.DurationTaskEntry;
import de.bitrecycling.timeshizz.clientlib.task.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;


/**
 * tests (parts of) the client lib. requires a service running for now!
 * <p>
 * created by robo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeshizzClientLibEnd2EndTests {

    @Autowired
    ClientConnector clientConnector;
    @Autowired
    ProjectConnector projectConnector;
    @Autowired
    TaskConnector taskConnector;


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

    @Test
    public void testProjects() {
        Client client = new Client();
        client.setName("xyzClientName");
        client.setAddress("xyzClientAddress");


        StepVerifier.create(clientConnector.createClient(client)).expectNextMatches(
                client1 -> {
                    client.setId(client1.getId());
                    return client.getName().equals(client.getName());
                }
        ).verifyComplete();

        Project test = new Project();
        test.setDescription("projectDescription");
        test.setName("projectName");
        test.setClientId(client.getId());

        StepVerifier.create(projectConnector.createProject(test)).expectNextMatches(
                project -> {
                    test.setId(project.getId());
                    return test.getName().equals(project.getName());
                }
        ).verifyComplete();


        StepVerifier.create(projectConnector.loadAllProjects()).expectNextMatches(
                project -> test.getId().equals(project.getId()
                )).expectNext().verifyComplete();


        StepVerifier.create(
                clientConnector.deleteClient(client.getId()
                )).expectNext().verifyComplete();
        StepVerifier.create(
                projectConnector.deleteProject(test.getId()
                )).expectNext().verifyComplete();
    }

    @Test
    public void testTasks() {

        //client
        Client client = new Client();
        client.setName("xyzClientName");
        client.setAddress("xyzClientAddress");


        StepVerifier.create(clientConnector.createClient(client)).expectNextMatches(
                client1 -> {
                    client.setId(client1.getId());
                    return client.getName().equals(client.getName());
                }
        ).verifyComplete();


        //project
        Project project1 = new Project();
        project1.setDescription("projectDescription");
        project1.setName("projectName");
        project1.setClientId(client.getId());

        StepVerifier.create(projectConnector.createProject(project1)).expectNextMatches(
                project -> {
                    project1.setId(project.getId());
                    return project1.getName().equals(project.getName());
                }
        ).verifyComplete();


        //task
        Task test = new Task();
        test.setName("testTask");
        test.setProjectId(project1.getId());

        StepVerifier.create(taskConnector.createTask(test)).expectNextMatches(
                task -> {
                    test.setId(task.getId());
                    return test.getName().equals(task.getName());
                }
        ).verifyComplete();

        StepVerifier.create(taskConnector.loadAllTasks()).expectNextMatches(
                task -> test.getId().equals(task.getId())
        ).verifyComplete();


        //taskEntry
        DurationTaskEntry testEntry = new DurationTaskEntry();
        testEntry.setDurationMinutes(42);
        testEntry.setTaskId(test.getId());
        StepVerifier.create(taskConnector.createTaskEntry(testEntry)).expectNextMatches(
                taskEntry -> {
                    testEntry.setId(taskEntry.getId());
                    return testEntry.getDurationMinutes().equals(42);

                }
        ).verifyComplete();


        // cleanup and verify
        StepVerifier.create(
                taskConnector.deleteTaskEntry(testEntry.getId())
        ).expectNext().verifyComplete();

        StepVerifier.create(
                taskConnector.deleteTask(test.getId())
        ).expectNext().verifyComplete();

        StepVerifier.create(
                projectConnector.deleteProject(project1.getId())
        ).expectNext().verifyComplete();
        StepVerifier.create(

                clientConnector.deleteClient(client.getId())
        ).expectNext().verifyComplete();

    }

}
