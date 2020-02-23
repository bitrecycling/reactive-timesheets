package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.TaskEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    


    @Test
    public void fullTurnaround() {
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress",LocalDateTime.now());
        clientRepository.save(c);
        ProjectEntity p = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        projectRespository.save(p);
        TaskEntity t = new TaskEntity(null,"fullTestTaskName",p, LocalDateTime.now());
        taskRepository.save(t);
        TaskEntryEntity te1 = new TaskEntryEntity(null,LocalDateTime.now(),120, t, LocalDateTime.now());
        TaskEntryEntity te2 = new TaskEntryEntity(null,LocalDateTime.now(),180, t,LocalDateTime.now());
        taskEntryRepository.save(te1);
        taskEntryRepository.save(te2);

        final Iterable<ClientEntity> all = clientRepository.findAll();
        //expect 1st client is c
        assertThat(all).hasSize(1);
        assertThat(all.iterator().next()).isEqualTo(c);
        final List<TaskEntryEntity> taskId = taskEntryRepository.findAllByTaskId(t.getId());
        //expect 2 results
        assertThat(taskId).hasSize(2);
        assertThat(taskId).containsExactlyInAnyOrder(te1,te2);
    }
//
//    /**
//     * tests projects by particular client
//     */
//    @Test
//    public void testProjectsByClient() {
//        ClientEntity c = createTestData();
//        StepVerifier.create(projectRespository.findAllByClientId(c.getId()))
//                .expectNextMatches(
//                        project -> project.getName().equals("fullTestProjectName"))
//                .verifyComplete();
//    }
//
//    /**
//     * tests tasks created between given timespan
//     */
//    @Test
//    public void testTasks(){
//        LocalDateTime before = LocalDateTime.now().minusSeconds(2);
//        TaskEntity t1 = new TaskEntity(null, "t1", "pid1", "cid1", before);
//        TaskEntity t2 = new TaskEntity(null, "t2", "pid1","cid1",before);
//        TaskEntity t3 = new TaskEntity(null,"t3", "pid2","cid1",before);
//        TaskEntity t4 = new TaskEntity(null,"t4","pid2","cid1",before);
//        TaskEntity t5 = new TaskEntity(null,"t5", "pid2","cid1",before);
//        List<TaskEntity> tasks = Arrays.asList(t1, t2, t3, t4, t5);
//        tasks.forEach(task -> taskRepository.save(task));
//        StepVerifier.create(taskRepository.findAllByProjectIdOrderByCreationTimeDesc("pid1")).expectNextCount(2).verifyComplete();
//        StepVerifier.create(taskRepository.findAllByProjectIdOrderByCreationTimeDesc("pid2")).expectNextCount(3).verifyComplete();
//        StepVerifier.create(taskRepository.findByCreationTimeBetween(before.minusSeconds(1), LocalDateTime.now()))
//                .expectNextCount(5).verifyComplete();
//        StepVerifier.create(taskRepository.findByCreationTimeBetween(before, before))
//                .expectNextCount(0).verifyComplete();
//        t1.setCreationTime(LocalDateTime.now().minusMinutes(1));
//        t2.setCreationTime(LocalDateTime.now().minusMinutes(1));
//        t5.setCreationTime(LocalDateTime.now().minusMinutes(1));
//        tasks.forEach(task -> taskRepository.save(task));
//        StepVerifier.create(taskRepository.findByCreationTimeBetween(before.minusMinutes(1), before))
//                .expectNextCount(3).verifyComplete();
//    }
//
//
//
//    /**
//     * tests taskEntries for client and created between given timespan
//     */
//    @Test
//    public void testTaskEntriesForClientBetween(){
//        LocalDateTime startTime = LocalDateTime.now();
//        LocalDateTime beforeStartTime = startTime.minusSeconds(1);
//        LocalDateTime afterStartTime = startTime.plusSeconds(1);
//        LocalDateTime startTimePlus1Hr = startTime.plusHours(1);
//        LocalDateTime beforeStartTimePlus1Hr = startTimePlus1Hr.minusSeconds(1);
//        LocalDateTime afterStartTimePlus1Hr = startTimePlus1Hr.plusSeconds(1);
//
//        TaskEntryEntity te1 = new TaskEntryEntity(startTime, 60, "t1", "pid1", "cid1");
//        TaskEntryEntity te2 = new TaskEntryEntity(startTimePlus1Hr, 30, "t2", "pid1", "cid1");
//        TaskEntryEntity te3 = new TaskEntryEntity(startTime, 60, "t1", "pid1", "cid2");
//        TaskEntryEntity te4 = new TaskEntryEntity(startTimePlus1Hr, 45, "t1", "pid1", "cid2");
//        TaskEntryEntity te5 = new TaskEntryEntity(afterStartTimePlus1Hr, 30, "t1", "pid1", "cid2");
//
//        List<TaskEntryEntity> taskEntries = Arrays.asList(te1, te2, te3, te4, te5);
//        taskEntries.forEach(task -> taskEntryRepository.save(task));
//        StepVerifier.create(
//                taskEntryRepository.findAllByClientIdAndStartTimeBetweenOrderByStartTimeDesc(
//                        "cid1", beforeStartTime, afterStartTime)
//        ).expectNextMatches(taskEntry -> te1.equals(taskEntry)).verifyComplete();
//
//        StepVerifier.create(
//                taskEntryRepository.findAllByClientIdAndStartTimeBetweenOrderByStartTimeDesc(
//                        "cid1", beforeStartTime, startTimePlus1Hr)
//        ).expectNextCount(1).verifyComplete();
//
//        StepVerifier.create(
//                taskEntryRepository.findAllByClientIdAndStartTimeBetweenOrderByStartTimeDesc(
//                        "cid1", beforeStartTime, afterStartTimePlus1Hr)
//        ).expectNextCount(2).verifyComplete();
//        StepVerifier.create(
//                taskEntryRepository.findAllByClientIdAndStartTimeBetweenOrderByStartTimeDesc(
//                        "cid1", beforeStartTimePlus1Hr, afterStartTimePlus1Hr)
//        ).expectNextMatches(taskEntry -> te2.equals(taskEntry)).verifyComplete();
//
//
//    }
//
//
//    /**
//     * tests ordering of taskentries by creation time
//     */
//    @Test
//    public void testLatestCreatedTaskEntries(){
//        ArrayList<TaskEntryEntity> taskEntries = new ArrayList<>();
//
//        for(int i=1; i<=20; i++){
//            TaskEntryEntity taskEntry = new TaskEntryEntity("id_" + i, LocalDateTime.parse("2018-11-11T15:30"), 30 + i,
//                    "nah" + (20 - i),"pid1","cid1", LocalDateTime.parse("2018-11-11T15:30").minusMinutes(i));
//            taskEntries.add(taskEntry);
//            if(i%2==0)
//                taskEntryRepository.save(taskEntry);
//        }
//        for(int i=1; i<=20; i++){
//            if(i%2==1)
//                taskEntryRepository.save(taskEntries.get(i-1));
//        }
//
//        Pageable pageable = Pageable.unpaged();
//        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable))
//                .expectNextMatches(
//                taskEntry -> taskEntries.get(19).equals(taskEntry)
//        ).expectNextMatches(
//                taskEntry -> taskEntries.get(18).equals(taskEntry))
//                .expectNextCount(18).verifyComplete();
//
//        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable))
//                .expectNextMatches(
//                        taskEntry -> taskEntries.get(0).equals(taskEntry)
//                ).expectNextMatches(
//                taskEntry -> taskEntries.get(1).equals(taskEntry))
//                .expectNextCount(18).verifyComplete();
//
//        pageable = PageRequest.of(1,10);
//        StepVerifier.create(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable))
//                .expectNextCount(10).verifyComplete();
//    }
//
//    private ClientEntity createTestData() {
//        ClientEntity c = new ClientEntity("fullTestClient","fullTestClientAddress");
//        c.setId("clientId");
//        clientRepository.save(c);
//        ProjectEntity p = new ProjectEntity("fullTestProjectName","fullTestProjectDescription",60.0,c.getId());
//        p.setId("projectId");
//        projectRespository.save(p);
//        TaskEntity t = new TaskEntity("fullTestTaskName",p.getId(),"cid");
//        t.setId("taskId");
//        taskRepository.save(t);
//        TaskEntryEntity te1 = new TaskEntryEntity(LocalDateTime.now(),120, t.getId(), "cid", "pid");
//        TaskEntryEntity te2 = new TaskEntryEntity(LocalDateTime.now(),180, t.getId(),"cid", "pid");
//        taskEntryRepository.save(te1);
//        taskEntryRepository.save(te2);
//        return c;
//    }
}
