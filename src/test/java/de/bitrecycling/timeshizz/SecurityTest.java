package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.management.api.ClientController;
import de.bitrecycling.timeshizz.management.api.ClientMapper;
import de.bitrecycling.timeshizz.management.api.CreateUserController;
import de.bitrecycling.timeshizz.management.api.UserMapper;
import de.bitrecycling.timeshizz.management.model.Client;
import de.bitrecycling.timeshizz.management.repository.UserRepository;
import de.bitrecycling.timeshizz.management.service.ClientService;
import de.bitrecycling.timeshizz.management.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CreateUserController.class, ClientController.class})
//@ContextConfiguration(classes = {UserService.class, SpringSecurityConf.class})
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserMapper userMapper;
    @InjectMocks
    CreateUserController createUserController;
    @MockBean
    ClientService clientService;
    @MockBean
    ClientMapper clientMapper;
    @InjectMocks
    ClientController clientController;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createUserIsPublic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"clientId\": \"1\",\"name\":\"username\",\"email\":\"bitrecycling@posteo.de\",\"password\":\"passw0rt\"}"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser()
    public void getClientsAuthorized() throws Exception {
        Mockito.when(clientService.all()).thenReturn(Lists.list(new Client(UUID.randomUUID(), "", "", LocalDateTime.now(), null, null)));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
