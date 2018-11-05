package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeshizzApplication.class)
@AutoConfigureMockMvc
public class RepositoryTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProjectRespository projectRespository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskEntryRepository taskEntryRepository;

    @After
    public void teardown() {
        clientRepository.deleteAll().subscribe();
        projectRespository.deleteAll().subscribe();
        taskRepository.deleteAll().subscribe();
        taskEntryRepository.deleteAll().subscribe();
    }


    @Test
    public void fullTurnaround() {
        Client c = Client.builder().name("fullTestClient").address("fullTestClientAddress").build();
        c.setId("clientId");
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
        StepVerifier.create(clientRepository.findAll()).expectNextMatches(
                client -> {
                   return c.equals(client);
                }).verifyComplete();

    }

    @Test
    public void testProjectsByClient() {
        Client c = createTestData();
        StepVerifier.create(projectRespository.findAllByClientId(c.getId()))
                .expectNextMatches(
                        project -> project.getName().equals("fullTestProjectName"))
                .verifyComplete();
    }

    private Client createTestData() {
        Client c = Client.builder().name("fullTestClient").address("fullTestClientAddress").build();
        c.setId("clientId");
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
