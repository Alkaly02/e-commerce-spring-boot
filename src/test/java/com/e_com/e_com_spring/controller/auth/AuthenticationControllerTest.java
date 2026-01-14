package com.e_com.e_com_spring.controller.auth;

import com.e_com.e_com_spring.EComSpringApplication;
import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.dto.auth.LoginResponseDto;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = EComSpringApplication.class
)
@AutoConfigureMockMvc
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

    private RegisterPostDto registerPostDto;
    private LoginPostDto loginPostDto;

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

        loginPostDto = new LoginPostDto();
        loginPostDto.setEmail("mocked@gmail.com");
        loginPostDto.setPassword("passer123");
    }

    @AfterEach
    void tearDown(){
        registerPostDto = null;
        loginPostDto = null;
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

    @Nested
    class LoginTests{
        @Test
        void shouldLogin_SuccessFully() throws Exception{
            mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerPostDto))
            );
            MvcResult result = mockMvc.perform(
                    post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(loginPostDto))
            )
                    .andExpect(status().isOk())
                    .andReturn();

            LoginResponseDto loginResponseDto = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    LoginResponseDto.class
            );

            assertNotNull(loginResponseDto);
            assertNotNull(loginResponseDto.getAccessToken());
            assertNotNull(loginResponseDto.getUser());
        }

        @Test
        void shouldNotLogin_WhenEmailDoesNotExists() throws Exception{
            loginPostDto.setEmail("notExist@gmail.com");

            mockMvc.perform(
                    post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginPostDto))
            )
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("email or password incorrect"));
        }
    }

}