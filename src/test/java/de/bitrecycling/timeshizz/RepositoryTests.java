package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.activity.repository.ActivityEntryRepository;
import de.bitrecycling.timeshizz.activity.repository.ActivityRepository;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * general test for model, persistency and relations work as expected
 * 
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
    ActivityRepository activityRepository;
    @Autowired
    ActivityEntryRepository activityEntryRepository;
    


    @Transactional
    @Test
    public void relationshipsOK() {
        UUID userId = UUID.randomUUID();
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress",LocalDateTime.now(), null, userId);
        clientRepository.save(c);
        ProjectEntity p = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c, null,userId);
        projectRespository.save(p);
        ActivityEntity t = new ActivityEntity(null,"fullTestActivityName", p, null, LocalDateTime.now(), userId);
        activityRepository.save(t);
        ActivityEntryEntity te1 = new ActivityEntryEntity(null,LocalDateTime.now(),120, t, LocalDateTime.now(), userId);
        ActivityEntryEntity te2 = new ActivityEntryEntity(null,LocalDateTime.now(),180, t,LocalDateTime.now(),userId);
        activityEntryRepository.save(te1);
        activityEntryRepository.save(te2);
        activityRepository.save(t);
        final List<ClientEntity> all = clientRepository.findAll();
        //expect 1st client is c
        assertThat(all).hasSize(1);
        assertThat(all.iterator().next()).isEqualTo(c);
        final List<ActivityEntryEntity> allByActivityId = activityEntryRepository.findAllByActivityId(t.getId());
        //expect 2 results
        assertThat(allByActivityId).hasSize(2);
        assertThat(allByActivityId).containsExactlyInAnyOrder(te1,te2);
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
    public void testProjectActivitysAndCreationTimeQueries(){
        UUID userId = UUID.randomUUID();
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null,userId);
        clientRepository.save(c);
        ProjectEntity p1 = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c, null,userId);
        ProjectEntity p2 = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c,null,userId);
        projectRespository.save(p1);
        projectRespository.save(p2);

        LocalDateTime before = LocalDateTime.now().minusSeconds(2);
        ActivityEntity t1 = new ActivityEntity(null, "t1", p1, null,before,userId);
        ActivityEntity t2 = new ActivityEntity(null, "t2", p1, null,before,userId);
        ActivityEntity t3 = new ActivityEntity(null,"t3", p2, null,before,userId);
        ActivityEntity t4 = new ActivityEntity(null,"t4",p2, null,before,userId);
        ActivityEntity t5 = new ActivityEntity(null,"t5", p2, null,before,userId);
        List<ActivityEntity> activities = Arrays.asList(t1, t2, t3, t4, t5);
        activities.forEach(activity-> activityRepository.save(activity));
        assertThat(activityRepository.findAllByProjectIdOrderByCreationTimeDesc(p1.getId()).size()).isEqualTo(2);
        assertThat(activityRepository.findAllByProjectIdOrderByCreationTimeDesc(p2.getId()).size()).isEqualTo(3);
        assertThat(activityRepository.findByCreationTimeBetween(before.minusSeconds(1), LocalDateTime.now()).size()).isEqualTo(5);
        assertThat(activityRepository.findByCreationTimeBetween(before.minusNanos(2), before.minusNanos(1)).size()).isEqualTo(0);
        t1.setCreationTime(before.minusMinutes(1));
        t2.setCreationTime(before.minusMinutes(1));
        t5.setCreationTime(before.minusMinutes(1));
        activities.forEach(activity-> activityRepository.save(activity));
        assertThat(activityRepository.findByCreationTimeBetween(before.minusMinutes(2), before.minusSeconds(1)).size()).isEqualTo(3);
    }


    /**
     * tests ordering of taskentries by creation time
     */
    @Transactional
    @Test
    public void testLatestCreatedActivityEntries(){
        UUID userId = UUID.randomUUID();
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null,userId);
        clientRepository.save(c);
        ProjectEntity p= new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c, null,userId);
        projectRespository.save(p);
        ArrayList<ActivityEntryEntity> taskEntries = new ArrayList<>();
        ActivityEntity t = new ActivityEntity(null,"fullTestActivityName",p,null,LocalDateTime.now(),userId);
        activityRepository.save(t);
        for(int i=1; i<=20; i++){
            ActivityEntryEntity taskEntry = new ActivityEntryEntity(null, LocalDateTime.parse("2018-11-11T15:30"), 60, t, LocalDateTime.parse("2018-11-11T15:30").minusMinutes(i),userId);
            taskEntries.add(taskEntry);
            if(i%2==0)
                activityEntryRepository.save(taskEntry);
        }
        for(int i=1; i<=20; i++){
            if(i%2==1)
                activityEntryRepository.save(taskEntries.get(i-1));
        }

        Pageable pageable = Pageable.unpaged();
        assertThat(activityEntryRepository.findAllByOrderByCreationTimeAsc(pageable).get(0)).isEqualTo(taskEntries.get(19));
        assertThat(activityEntryRepository.findAllByOrderByCreationTimeAsc(pageable).get(1)).isEqualTo(taskEntries.get(18));
        assertThat(activityEntryRepository.findAllByOrderByCreationTimeDesc(pageable).get(0)).isEqualTo(taskEntries.get(0));
        assertThat(activityEntryRepository.findAllByOrderByCreationTimeDesc(pageable).get(1)).isEqualTo(taskEntries.get(1));
        pageable = PageRequest.of(1,10);
        assertThat(activityEntryRepository.findAllByOrderByCreationTimeAsc(pageable).size()).isEqualTo(10);
    }

    private ClientEntity createTestData() {
        UUID userId = UUID.randomUUID();
        ClientEntity c = new ClientEntity(null,"fullTestClient","fullTestClientAddress", LocalDateTime.now(), null,userId);
        clientRepository.save(c);
        ProjectEntity p = new ProjectEntity(null,"fullTestProjectName","fullTestProjectDescription",60.0,c,null,userId);
        projectRespository.save(p);
        ActivityEntity t = new ActivityEntity(null,"fullTestActivityName",p,null,LocalDateTime.now(),userId);
        activityRepository.save(t);
        ActivityEntryEntity te1 = new ActivityEntryEntity(null,LocalDateTime.now(),120, t, LocalDateTime.now(),userId);
        ActivityEntryEntity te2 = new ActivityEntryEntity(null,LocalDateTime.now(),180, t,LocalDateTime.now(),userId);
        activityEntryRepository.save(te1);
        activityEntryRepository.save(te2);
        return c;
    }
}
