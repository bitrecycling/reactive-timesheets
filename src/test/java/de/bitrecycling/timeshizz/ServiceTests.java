package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeshizzApplication.class)
public class ServiceTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProjectRespository projectRespository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskEntryRepository taskEntryRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    ProjectService projectService;
    @Autowired
    org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    @After
    public void teardown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void fullTurnaround() {
        Client c = createTestData();
        clientService.byName(c.getName()).subscribe();
        StepVerifier.create(
                clientService.all()).expectNextMatches(c::equals).verifyComplete();
        c.setAddress("newClientAddress");
        c.setName("newClientName");
        StepVerifier.create(
                clientService.update(c)
        ).expectNextMatches(

                client -> {
                    Client yo = new Client("clientId","newClientName", "newClientAddress",
                            LocalDateTime.of(1970, 1,1,1,1));
                    yo.setId(c.getId());
                    return client.equals(yo);
                }).verifyComplete();

        StepVerifier.create(clientService.delete("clientId")).expectNext().verifyComplete();
        System.out.println("done");
    }

    @Test
    public void testClientByName(){
        createTestData();
        StepVerifier.create(clientService.byName("clientName")).expectNextMatches(
            client -> "clientId".equals(client.getId())
        ).verifyComplete();
    }

    @Test
    public void testClientById(){
        createTestData();
        StepVerifier.create(clientService.byId("clientId")).expectNextMatches(
                client -> "clientId".equals(client.getId())
        ).verifyComplete();
    }

    @Test
    public void testProjectsByClient() {
        createTestData();
        StepVerifier.create(projectService.byClientName("clientName"))
                .expectNextMatches(
                        project -> project.getName().equals("fullTestProjectName"))
                .verifyComplete();
    }

    private Client createTestData() {
        Client c = new Client("clientId", "clientName", "clientAddress",
                LocalDateTime.of(1970, 1,1,1,1));
        clientRepository.insert(c).block();
        Project p = new Project("fullTestProjectName","fullTestProjectDescription",60.0,c.getId());
        p.setId("projectId");
        projectRespository.insert(p).block();
        Task t = new Task("fullTestTaskName",p.getId());
        t.setId("taskId");
        taskRepository.insert(t).block();
        TaskEntry te1 = new TaskEntry(LocalDateTime.now(),120, t.getId());
        te1.setCreationTime(LocalDateTime.now());
        TaskEntry te2 = new TaskEntry(LocalDateTime.now(),180, t.getId());
        te2.setCreationTime(LocalDateTime.now());
        taskEntryRepository.insert(te1).block();
        taskEntryRepository.insert(te2).block();
        return c;
    }
}
