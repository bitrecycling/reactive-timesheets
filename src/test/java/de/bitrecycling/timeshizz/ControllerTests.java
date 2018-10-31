package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.controller.ProjectController;
import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.model.Project;
import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.model.TaskEntry;
import de.bitrecycling.timeshizz.repository.ClientRepository;
import de.bitrecycling.timeshizz.repository.ProjectRespository;
import de.bitrecycling.timeshizz.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.repository.TaskRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
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
        webTestClient.get().uri("/projects").exchange().expectBody().json("[{\"id\":\"bcd\",\"name\":\"fullTestProjectName\",\"description\":\"fullTestProjectDescription\",\"rate\":100,\"taskIds\":[\"cde\"]}]");
    }

    @Test
    public void testClientController(){
        webTestClient.get().uri("/clients").exchange().expectBody().json("[{\"id\":\"abc\",\"name\":\"fullTestClient\",\"address\":\"fullTestClientAddress\",\"projectIds\":[\"bcd\"]}]");
    }

    private Client createTestData() {
        Client c = new Client();
        c.setName("fullTestClient");
        c.setAddress("fullTestClientAddress");
        c.setId("abc");
        clientRepository.insert(c).subscribe();
        Project p = new Project();
        p.setDescription("fullTestProjectDescription");
        p.setName("fullTestProjectName");
        p.setRate(100);
        p.setId("bcd");
        projectRespository.insert(p).subscribe();
        Task t = new Task();
        t.setName("fullTestTaskName");
        t.setCreationTime(LocalDateTime.now());
        t.setId("cde");
        taskRepository.insert(t).subscribe();
        TaskEntry te1 = new TaskEntry(LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        te1.setId("def");
        TaskEntry te2 = new TaskEntry(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(3)));
        te2.setId("efg");
        t.getTaskEntryIds().add(te1.getId());
        t.getTaskEntryIds().add(te2.getId());
        taskEntryRepository.insert(te1).subscribe();
        taskEntryRepository.insert(te2).subscribe();
        taskRepository.save(t).subscribe();
        p.getTaskIds().add(t.getId());
        projectRespository.save(p).subscribe();
        c.getProjectIds().add(p.getId());
        clientRepository.save(c).subscribe();
        return c;
    }

}
