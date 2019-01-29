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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Autowired
    org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    @After
    public void teardown() {
        mongoTemplate.getDb().drop();
    }


    @Test
    public void fullTurnaround() {
        Client c = new Client("fullTestClient","fullTestClientAddress");
        c.setId("clientId");
        clientRepository.insert(c).block();
        Project p = new Project("fullTestProjectName","fullTestProjectDescription",60.0,c.getId());
        p.setId("projectId");
        projectRespository.insert(p).block();
        Task t = new Task("fullTestTaskName",p.getId());
        t.setId("taskId");
        taskRepository.insert(t).block();
        TaskEntry te1 = new TaskEntry(LocalDateTime.now(),120, t.getId());
        TaskEntry te2 = new TaskEntry(LocalDateTime.now(),180, t.getId());
        taskEntryRepository.insert(te1).block();
        taskEntryRepository.insert(te2).block();
        StepVerifier.create(clientRepository.findAll()).expectNextMatches(
                client -> {
                   return c.equals(client);
                }).verifyComplete();

        StepVerifier.create(taskEntryRepository.findAllByTaskId("taskId"))
                .expectNextCount(2).verifyComplete();

    }

    /**
     * tests projects by particular client
     */
    @Test
    public void testProjectsByClient() {
        Client c = createTestData();
        StepVerifier.create(projectRespository.findAllByClientId(c.getId()))
                .expectNextMatches(
                        project -> project.getName().equals("fullTestProjectName"))
                .verifyComplete();
    }

    /**
     * tests tasks created between given timespan
     */
    @Test
    public void testTasks(){
        LocalDateTime before = LocalDateTime.now().minusSeconds(2);
        Task t1 = new Task(null, "t1", "pid1", before);
        Task t2 = new Task(null, "t2", "pid1",before);
        Task t3 = new Task(null,"t3", "pid2",before);
        Task t4 = new Task(null,"t4","pid2",before);
        Task t5 = new Task(null,"t5", "pid2",before);
        List<Task> tasks = Arrays.asList(t1, t2, t3, t4, t5);
        tasks.forEach(task -> taskRepository.insert(task).block());
        StepVerifier.create(taskRepository.findAllByProjectIdOrderByCreationTimeDesc("pid1")).verifyComplete();
        StepVerifier.create(taskRepository.findAllByProjectIdOrderByCreationTimeDesc("pid2")).expectNextCount(3).verifyComplete();
        StepVerifier.create(taskRepository.findByCreationTimeBetween(before.minusSeconds(1), LocalDateTime.now()))
                .expectNextCount(5).verifyComplete();
        StepVerifier.create(taskRepository.findByCreationTimeBetween(before, before))
                .expectNextCount(0).verifyComplete();
        t1.setCreationTime(LocalDateTime.now().minusMinutes(1));
        t2.setCreationTime(LocalDateTime.now().minusMinutes(1));
        t5.setCreationTime(LocalDateTime.now().minusMinutes(1));
        tasks.forEach(task -> taskRepository.save(task).block());
        StepVerifier.create(taskRepository.findByCreationTimeBetween(before.minusMinutes(1), before))
                .expectNextCount(3).verifyComplete();
    }


    /**
     * tests ordering of taskentries by creation time
     */
    @Test
    public void testLatestCreatedTaskEntries(){
        ArrayList<TaskEntry> taskEntries = new ArrayList<>();

        for(int i=1; i<=20; i++){
            TaskEntry taskEntry = new TaskEntry("id_" + i, LocalDateTime.parse("2018-11-11T15:30"), 30 + i,
                    "nah" + (20 - i), LocalDateTime.parse("2018-11-11T15:30").minusMinutes(i));
            taskEntries.add(taskEntry);
            if(i%2==0)
                taskEntryRepository.insert(taskEntry).block();
        }
        for(int i=1; i<=20; i++){
            if(i%2==1)
                taskEntryRepository.insert(taskEntries.get(i-1)).block();
        }

        Pageable pageable = Pageable.unpaged();
        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable))
                .expectNextMatches(
                taskEntry -> taskEntries.get(19).equals(taskEntry)
        ).expectNextMatches(
                taskEntry -> taskEntries.get(18).equals(taskEntry))
                .expectNextCount(18).verifyComplete();

        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable))
                .expectNextMatches(
                        taskEntry -> taskEntries.get(0).equals(taskEntry)
                ).expectNextMatches(
                taskEntry -> taskEntries.get(1).equals(taskEntry))
                .expectNextCount(18).verifyComplete();

        pageable = PageRequest.of(1,10);
        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable))
                .expectNextCount(10).verifyComplete();
    }

    private Client createTestData() {
        Client c = new Client("fullTestClient","fullTestClientAddress");
        c.setId("clientId");
        clientRepository.insert(c).block();
        Project p = new Project("fullTestProjectName","fullTestProjectDescription",60.0,c.getId());
        p.setId("projectId");
        projectRespository.insert(p).block();
        Task t = new Task("fullTestTaskName",p.getId());
        t.setId("taskId");
        taskRepository.insert(t).block();
        TaskEntry te1 = new TaskEntry(LocalDateTime.now(),120, t.getId());
        TaskEntry te2 = new TaskEntry(LocalDateTime.now(),180, t.getId());
        taskEntryRepository.insert(te1).block();
        taskEntryRepository.insert(te2).block();
        return c;
    }
}
