package de.bitrecycling.timeshizz;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateUserApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @BeforeAll
    public static void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup();
    }

    @Test
    public void createUserSuccessful() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clientId\": \"1\",\"name\":\"username\",\"email\":\"bitrecycling@posteo.de\",\"password\":\"passw0rt\"}")
                .post("/user")
                .then()
                .statusCode(200)
                .body(equalTo("{\"id\":1,\"name\":\"username\",\"email\":\"bitrecycling@posteo.de\"}"));
    }
    
}
