package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Optional;

import static com.e_com.e_com_spring.util.UserUtils.createRegisterPostDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private RegisterPostDto registerPostDto;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    static void beforeAll(){
        postgres.start();
    }
    @AfterAll
    static void afterAll(){
        postgres.stop();
    }

    @BeforeEach
    void setUp(){
        registerPostDto = createRegisterPostDto(
                "Mocked firstName",
                "Mocked lastName",
                "mocked@gmail.com",
                "passer123",
                "ROLE_ADMIN"
        );
    }

    @AfterEach
    void tearDown(){
        registerPostDto = null;
    }

    @Nested
    class DisableUserTests{
        @Test
        void shouldDisable() throws Exception{
            mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerPostDto))
            );
            // TODO: user updating should be an admin
            mockMvc.perform(
                            put("/users/{id}/disable", "1")
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("User disabled"));
            Optional<User> user = userRepository.findById(1L);
            assertTrue(user.isPresent());
            assertFalse(user.get().isEnabled());
        }

        @Test
        void shouldNotDisable_WhenUserNotAdmin() throws Exception{
            mockMvc.perform(
                    put("/users/{id}/disable", "1")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.message").value("You do not have permission to perform this action"));
        }
    }

    @Nested
    class EnableUserTests{
        @Test
        void shouldEnable() throws Exception{
            mockMvc.perform(
                            put("/users/{id}/enable", "1")
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("User enabled"));
            Optional<User> optionalUser = userRepository.findById(1L);
            assertTrue(optionalUser.isPresent());
            assertTrue(optionalUser.get().isEnabled());
        }
    }
}