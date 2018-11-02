package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.model.Project;
import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.model.TaskEntry;
import de.bitrecycling.timeshizz.repository.ClientRepository;
import de.bitrecycling.timeshizz.repository.ProjectRespository;
import de.bitrecycling.timeshizz.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.repository.TaskRepository;
import de.bitrecycling.timeshizz.service.ClientService;
import de.bitrecycling.timeshizz.service.ProjectService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;
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

    @After
    public void teardown() {
        clientRepository.deleteAll().subscribe();
        projectRespository.deleteAll().subscribe();
        taskRepository.deleteAll().subscribe();
        taskEntryRepository.deleteAll().subscribe();
    }


    @Test
    public void fullTurnaround() {
        Client c = createTestData();
        clientService.byName(c.getName()).subscribe();
        StepVerifier.create(
                clientService.all()).expectNextMatches(c::equals).verifyComplete();

        StepVerifier.create(
                clientService.update(c.getId(), "newClientName", "newClientAddress")
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
    public void testProjectsByClient() {
        createTestData();
        StepVerifier.create(projectService.byClientName("fullTestClient"))
                .expectNextMatches(
                        project -> project.getName().equals("fullTestProjectName"))
                .verifyComplete();
    }

    private Client createTestData() {
        Client c = new Client("clientId", "clientName", "clientAddress",
                LocalDateTime.of(1970, 1,1,1,1));
        clientRepository.insert(c).subscribe();
        Project p = Project.builder().name("fullTestProjectName")
                .description("fullTestProjectDescription").rate(100).clientId(c.getId()).build();
        p.setId("projectId");
        projectRespository.insert(p).subscribe();
        Task t = Task.builder().name("fullTestTaskName").projectId(p.getId()).build();
        t.setId("taskId");
        taskRepository.insert(t).subscribe();
        TaskEntry te1 = new TaskEntry(LocalDateTime.now(), LocalDateTime.now().plusHours(5), t.getId());
        TaskEntry te2 = new TaskEntry(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(3)), t.getId());
        taskEntryRepository.insert(te1).subscribe();
        taskEntryRepository.insert(te2).subscribe();
        return c;
    }
}
