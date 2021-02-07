package de.bitrecycling.timeshizz;

import de.bitrecycling.timeshizz.management.api.CreateUserController;
import de.bitrecycling.timeshizz.management.api.UserMapper;
import de.bitrecycling.timeshizz.management.repository.UserRepository;
import de.bitrecycling.timeshizz.management.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateUserController.class)
//@ContextConfiguration(classes = {UserService.class})
public class CreateUserApiTest {

    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserMapper userMapper;
    @InjectMocks
    CreateUserController createUserController;
    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup();
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\": \"1\",\"name\":\"username\",\"email\":\"bitrecycling@posteo.de\",\"password\":\"passw0rt\"}"))
                .andExpect(status().isOk());
    }

}
