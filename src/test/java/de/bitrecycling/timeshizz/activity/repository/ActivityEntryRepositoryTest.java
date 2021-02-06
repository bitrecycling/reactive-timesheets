package de.bitrecycling.timeshizz.activity.repository;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * specific test for the custom queries in activity entry repository
 */
@SpringBootTest
class ActivityEntryRepositoryTest {

    @Autowired
    ActivityEntryRepository activityEntryRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ProjectRespository projectRespository;
    @Autowired
    ClientRepository clientRepository;
    
    @Transactional
    @Test
    void customReportQueryTest() {
        //given 1 client with 2 projects, each of them with 1 activity, one of which has 2, and the other one 1 activity entries
        //and another client with 1 project but no activities / activity entries
        ClientEntity c1 = new ClientEntity();
        c1.setName("testclient");
        c1.setAddress("testaddress");
        clientRepository.save(c1);

        ClientEntity c2 = new ClientEntity();
        c2.setName("testclient");
        c2.setAddress("testaddress");
        clientRepository.save(c2);

        ProjectEntity pc2 = new ProjectEntity();
        pc2.setDescription("p2 description");
        pc2.setName("p2 name");
        pc2.setRate(333d);
        pc2.setClient(c2);
        projectRespository.save(pc2);
        
        ProjectEntity p1 = new ProjectEntity();
        p1.setDescription("p1 description");
        p1.setName("p1 name");
        p1.setRate(333d);
        p1.setClient(c1);
        projectRespository.save(p1);
        
        
        ProjectEntity p2 = new ProjectEntity();
        p2.setDescription("p2 description");
        p2.setName("p2 name");
        p2.setRate(333d);
        p2.setClient(c1);
        projectRespository.save(p2);

        c1.setProjects(Lists.list(p1,p2));
        clientRepository.save(c1);
        
        ActivityEntity a1 = new ActivityEntity();
        a1.setProject(p1);
        a1.setName("a1 activity");
        activityRepository.save(a1);
        ActivityEntity a2 = new ActivityEntity();
        a2.setProject(p2);
        a2.setName("a2 activity");
        activityRepository.save(a2);
        
        p1.setActivities(Lists.list(a1));
        p2.setActivities(Lists.list(a2));
        projectRespository.save(p1);
        projectRespository.save(p2);
        

        ActivityEntryEntity ae1 = new ActivityEntryEntity();
        ae1.setStartTime(LocalDateTime.now().minusSeconds(30));
        ae1.setActivity(a1);
        ae1.setDurationMinutes(60);
        activityEntryRepository.save(ae1);
        ActivityEntryEntity ae2 = new ActivityEntryEntity();
        ae2.setStartTime(LocalDateTime.now().minusSeconds(5));
        ae2.setActivity(a1);
        ae2.setDurationMinutes(30);
        activityEntryRepository.save(ae2);
        a1.setActivityEntries(Lists.list(ae1, ae2));
        activityRepository.save(a1);

        ActivityEntryEntity ae3 = new ActivityEntryEntity();
        ae3.setStartTime(LocalDateTime.now().minusSeconds(5));
        ae3.setActivity(a2);
        ae3.setDurationMinutes(66);
        activityEntryRepository.save(ae3);
        a2.setActivityEntries(Lists.list(ae3));
        activityRepository.save(a2);
        
        
        
        //this could (and should?) be done with "simple" jpa interface query as well
        final List<ActivityEntryEntity> activityEntriesForClientAndProject = activityEntryRepository.findActivityEntriesForActivityBetween(a2.getId(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        assertThat(activityEntriesForClientAndProject.size()).isEqualTo(1);
        
        //when all activity entries for project with a timeframe are queried, then 2 are expected
        final List<ActivityEntryEntity> two = activityEntryRepository.findActivityEntriesForProjectBetween(p1,LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        assertThat(two).containsExactlyInAnyOrder(ae1,ae2);

        //when all activity entries for project with a small enough timeframe are queried, then 1 are expected
        final List<ActivityEntryEntity> watchTheTime = activityEntryRepository.findActivityEntriesForProjectBetween(p1,LocalDateTime.now().minusSeconds(25), LocalDateTime.now());
        assertThat(watchTheTime).containsExactlyInAnyOrder(ae2);

        //when all activity entries for project with wrong timeframe are queried, then 0 are expected
        final List<ActivityEntryEntity> none = activityEntryRepository.findActivityEntriesForActivityBetween(a1.getId(), LocalDateTime.now(), LocalDateTime.now());
        assertThat(none.size()).isZero();

        //when all activity entries for other project are queried with long enough timeframe, then the only one is expected
        final List<ActivityEntryEntity> one = activityEntryRepository.findActivityEntriesForProjectBetween(p2,LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        assertThat(one).containsExactly(ae3);

        //when all activity entries for client are queried, then all 3 are expected
        final List<ActivityEntryEntity> three = activityEntryRepository.findActivityEntriesForClientBetween(c1,LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        assertThat(three).containsExactly(ae1,ae2,ae3);

        // when all activity entries for client with none are queried, none are expected
        final List<ActivityEntryEntity> c2none = activityEntryRepository.findActivityEntriesForClientBetween(c2,LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        assertThat(c2none).isEmpty();

    }
}