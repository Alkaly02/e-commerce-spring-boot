package com.e_com.e_com_spring.controller.auth;

import com.e_com.e_com_spring.EComSpringApplication;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EComSpringApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.yml"
)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    RegisterPostDto registerPostDto;

    @BeforeEach
    void setup(){
        registerPostDto = new RegisterPostDto();
        registerPostDto.setFirstName("Mocked firstName");
        registerPostDto.setLastName("Mocked lastName");
        registerPostDto.setEmail("mocked@gmail.com");
        registerPostDto.setPassword("passer123");
        registerPostDto.setRoleType("ROLE_ADMIN");
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
    }

}