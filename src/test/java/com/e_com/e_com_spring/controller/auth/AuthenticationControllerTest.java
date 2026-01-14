package com.e_com.e_com_spring.controller.auth;

import com.e_com.e_com_spring.EComSpringApplication;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = EComSpringApplication.class
)
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.yml"
//)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");


    RegisterPostDto registerPostDto;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "http://localhost:" + port;
        registerPostDto = new RegisterPostDto();
        registerPostDto.setFirstName("Mocked firstName");
        registerPostDto.setLastName("Mocked lastName");
        registerPostDto.setEmail("mocked@gmail.com");
        registerPostDto.setPassword("passer123");
        registerPostDto.setRoleType("ROLE_ADMIN");
    }

    @AfterEach
    void tearDown(){
        registerPostDto = null;
    }

    @Nested
    class RegisterTests{
        @Test
        void shouldRegister_SuccessFully() throws Exception{
            mockMvc.perform(
                            post("/auth/register")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(registerPostDto))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("User successfully registered"));

            assertTrue(userRepository.findByEmail("mocked@gmail.com").isPresent());
        }

        @Test
        void shouldNotRegister_WhenEmailAlreadyExists() throws Exception {
            mockMvc.perform(
                            post("/auth/register")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(registerPostDto))
                    )
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value("User with this email already exists"));
        }

        @Test
        void shouldNotRegister_WhenRoleTypeNotValid() throws Exception{
            registerPostDto.setEmail("mocked.clone@gmail.com");
            registerPostDto.setRoleType("ROLE_INVALID");
            mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerPostDto))
            )
                    .andExpect(status().isBadRequest());
        }
    }

}