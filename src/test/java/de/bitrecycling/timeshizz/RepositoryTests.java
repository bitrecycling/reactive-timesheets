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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * these tests just ensure, that the db, models and queries work as expected
 */
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
    


    @Transactional
    @Test
    public void relationshipsOK() {
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress",LocalDateTime.now(), null);
        clientRepository.save(c);
        ProjectEntity p = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        projectRespository.save(p);
        TaskEntity t = new TaskEntity(null,"fullTestTaskName", p, null, LocalDateTime.now());
        taskRepository.save(t);
        TaskEntryEntity te1 = new TaskEntryEntity(null,LocalDateTime.now(),120, t, LocalDateTime.now());
        TaskEntryEntity te2 = new TaskEntryEntity(null,LocalDateTime.now(),180, t,LocalDateTime.now());
        taskEntryRepository.save(te1);
        taskEntryRepository.save(te2);
        taskRepository.save(t);
        final List<ClientEntity> all = clientRepository.findAll();
        //expect 1st client is c
        assertThat(all).hasSize(1);
        assertThat(all.iterator().next()).isEqualTo(c);
        final List<TaskEntryEntity> allByTaskId = taskEntryRepository.findAllByTaskId(t.getId());
        //expect 2 results
        assertThat(allByTaskId).hasSize(2);
        assertThat(allByTaskId).containsExactlyInAnyOrder(te1,te2);
    }

    /**
     * tests projects by particular client
     */
    @Test
    public void testProjectsByClientQuery() {
        ClientEntity c = createTestData();
        final List<ProjectEntity> allByClientId = projectRespository.findAllByClientId(c.getId());
        assertThat(allByClientId.iterator().next().getName()).isEqualTo("fullTestProjectName");
    }

    /**
     * tests tasks created between given timespan
     */
    @Test
    public void testProjectTasksAndCreationTimeQueries(){
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null);
        clientRepository.save(c);
        ProjectEntity p1 = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        ProjectEntity p2 = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        projectRespository.save(p1);
        projectRespository.save(p2);

        LocalDateTime before = LocalDateTime.now().minusSeconds(2);
        TaskEntity t1 = new TaskEntity(null, "t1", p1, null,before);
        TaskEntity t2 = new TaskEntity(null, "t2", p1, null,before);
        TaskEntity t3 = new TaskEntity(null,"t3", p2, null,before);
        TaskEntity t4 = new TaskEntity(null,"t4",p2, null,before);
        TaskEntity t5 = new TaskEntity(null,"t5", p2, null,before);
        List<TaskEntity> tasks = Arrays.asList(t1, t2, t3, t4, t5);
        tasks.forEach(task -> taskRepository.save(task));
        assertThat(taskRepository.findAllByProjectIdOrderByCreationTimeDesc(p1.getId()).size()).isEqualTo(2);
        assertThat(taskRepository.findAllByProjectIdOrderByCreationTimeDesc(p2.getId()).size()).isEqualTo(3);
        assertThat(taskRepository.findByCreationTimeBetween(before.minusSeconds(1), LocalDateTime.now()).size()).isEqualTo(5);
        assertThat(taskRepository.findByCreationTimeBetween(before.minusNanos(2), before.minusNanos(1)).size()).isEqualTo(0);
        t1.setCreationTime(before.minusMinutes(1));
        t2.setCreationTime(before.minusMinutes(1));
        t5.setCreationTime(before.minusMinutes(1));
        tasks.forEach(task -> taskRepository.save(task));
        assertThat(taskRepository.findByCreationTimeBetween(before.minusMinutes(2), before.minusSeconds(1)).size()).isEqualTo(3);
    }


    /**
     * tests ordering of taskentries by creation time
     */
    @Transactional
    @Test
    public void testLatestCreatedTaskEntries(){
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null);
        clientRepository.save(c);
        ProjectEntity p= new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        projectRespository.save(p);
        ArrayList<TaskEntryEntity> taskEntries = new ArrayList<>();
        TaskEntity t = new TaskEntity(null,"fullTestTaskName",p,null,LocalDateTime.now());
        taskRepository.save(t);
        for(int i=1; i<=20; i++){
            TaskEntryEntity taskEntry = new TaskEntryEntity(null, LocalDateTime.parse("2018-11-11T15:30"), 60, t, LocalDateTime.parse("2018-11-11T15:30").minusMinutes(i));
            taskEntries.add(taskEntry);
            if(i%2==0)
                taskEntryRepository.save(taskEntry);
        }
        for(int i=1; i<=20; i++){
            if(i%2==1)
                taskEntryRepository.save(taskEntries.get(i-1));
        }

        Pageable pageable = Pageable.unpaged();
        assertThat(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable).get(0)).isEqualTo(taskEntries.get(19));
        assertThat(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable).get(1)).isEqualTo(taskEntries.get(18));
        assertThat(taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable).get(0)).isEqualTo(taskEntries.get(0));
        assertThat(taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable).get(1)).isEqualTo(taskEntries.get(1));
        pageable = PageRequest.of(1,10);
        assertThat(taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable).size()).isEqualTo(10);
    }

    private ClientEntity createTestData() {
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null);
        clientRepository.save(c);
        ProjectEntity p = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c);
        projectRespository.save(p);
        TaskEntity t = new TaskEntity(null,"fullTestTaskName",p,null,LocalDateTime.now());
        taskRepository.save(t);
        TaskEntryEntity te1 = new TaskEntryEntity(null,LocalDateTime.now(),120, t, LocalDateTime.now());
        TaskEntryEntity te2 = new TaskEntryEntity(null,LocalDateTime.now(),180, t,LocalDateTime.now());
        taskEntryRepository.save(te1);
        taskEntryRepository.save(te2);
        return c;
    }
}
