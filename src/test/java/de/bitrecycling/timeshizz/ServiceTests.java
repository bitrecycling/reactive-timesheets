package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
import de.bitrecycling.timeshizz.task.service.TaskService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeshizzApplication.class)
public class ServiceTests {

    @Autowired
    ClientService clientService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskEntryService taskEntryService;


    @After
    public void teardown() {

    }

    @Test
    public void testContext(){

    }

    /**
     * tests if exception is thrown if resource not found. The exception is mapped to 404 response
     */
    @Test
    public void testError(){
        StepVerifier.create(clientService.byId("n/a")).verifyError(ResourceNotFoundException.class);
        StepVerifier.create(projectService.byId("n/a")).verifyError(ResourceNotFoundException.class);
        StepVerifier.create(taskService.byId("n/a")).verifyError(ResourceNotFoundException.class);
        StepVerifier.create(taskEntryService.byId("n/a")).verifyError(ResourceNotFoundException.class);
    }

}
