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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ControllerTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProjectRespository projectRespository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskEntryRepository taskEntryRepository;
    @Autowired
    WebTestClient webTestClient;

    @After
    public void teardown() {
        clientRepository.deleteAll().subscribe();
        projectRespository.deleteAll().subscribe();
        taskRepository.deleteAll().subscribe();
        taskEntryRepository.deleteAll().subscribe();
    }

    @Before
    public void setup() {
      Client testData = createTestData();
    }

    @Test
    public void testProjectController() {
        webTestClient.get().uri("/projects").exchange().expectBody()
        .jsonPath("$[0].name").isEqualTo("fullTestProjectName")
        .jsonPath("$[0].id").isEqualTo("projectId");
    }

    @Test
    public void testClientController(){
        webTestClient.get().uri("/clients").exchange().expectBody()
                .jsonPath("$[0].name").isEqualTo("fullTestClientName")
                .jsonPath("$[0].id").isEqualTo("clientId");
    }

    private Client createTestData() {
        Client c = Client.builder().name("fullTestClientName").address("fullTestClientAddress").build();
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
        TaskEntry te2 = new TaskEntry(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(3)),t.getId());
        taskEntryRepository.insert(te1).subscribe();
        taskEntryRepository.insert(te2).subscribe();
        return c;
    }

}
