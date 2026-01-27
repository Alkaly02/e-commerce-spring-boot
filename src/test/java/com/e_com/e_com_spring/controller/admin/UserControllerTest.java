package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.RoleRepository;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.bouncycastle.mime.Headers;

import java.util.Optional;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserUtils userUtils;

    private RegisterPostDto adminRegister;
    private RegisterPostDto customerRegister;
    private HttpHeaders adminHeaders;
    private HttpHeaders customerHeaders;
    private User admin;
    private User customer;

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
        adminRegister = userUtils.createRegisterPostDto(
                "Mocked firstName",
                "Mocked lastName",
                "mocked@gmail.com",
                "passer123",
                "ROLE_ADMIN"
        );
        customerRegister = userUtils.createRegisterPostDto(
                "Mocked Customer",
                "Customer lastname",
                "customer@gmail.com",
                "passer123",
                "ROLE_CUSTOMER"
        );
        admin = userUtils.registerUser(adminRegister);
        customer = userUtils.registerUser(customerRegister);
        adminHeaders = userUtils.getHttpHeaders(admin.getEmail());
        customerHeaders = userUtils.getHttpHeaders(customerRegister.getEmail());
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
        adminRegister = null;
        customerRegister = null;
        admin = null;
        customer = null;
        adminHeaders = null;
    }

    @Nested
    class DisableUserTests{
        @Test
        void shouldDisable() throws Exception{
            assertNotNull(customer, "Customer should not be null");
            assertNotNull(customer.getId(), "Customer ID should not be null");
            Optional<User> customerBefore = userRepository.findById(customer.getId());
            assertTrue(customerBefore.isPresent(), "Customer should exist in database before disable");
            
            mockMvc.perform(
                            put("/users/{id}/disable", customer.getId())
                                    .headers(adminHeaders)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.enabled").value(false))
                    .andExpect(jsonPath("$.id").value(customer.getId()));
            Optional<User> user = userRepository.findById(customer.getId());
            assertTrue(user.isPresent());
            assertFalse(user.get().isEnabled());
        }

        @Test
        void shouldNotDisable_WhenUserNotAdmin() throws Exception{
            assertNotNull(customer, "Customer should not be null");
            assertNotNull(customer.getId(), "Customer ID should not be null");
            Optional<User> customerBefore = userRepository.findById(customer.getId());
            assertTrue(customerBefore.isPresent(), "Customer should exist in database before disable");
            mockMvc.perform(
                    put("/users/{id}/disable", customer.getId())
                            .headers(customerHeaders)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.error").value("Access Denied"));
        }
    }

    @Nested
    class EnableUserTests{
        @Test
        void shouldEnable() throws Exception{
            // First disable the customer
            customer.setEnabled(false);
            userRepository.save(customer);
            userRepository.flush();
            
            assertNotNull(customer, "Customer should not be null");
            assertNotNull(customer.getId(), "Customer ID should not be null");
            
            mockMvc.perform(
                            put("/users/{id}/enable", customer.getId())
                                    .headers(adminHeaders)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.enabled").value(true))
                    .andExpect(jsonPath("$.id").value(customer.getId()));
            Optional<User> optionalUser = userRepository.findById(customer.getId());
            assertTrue(optionalUser.isPresent());
            assertTrue(optionalUser.get().isEnabled());
        }
    }
}