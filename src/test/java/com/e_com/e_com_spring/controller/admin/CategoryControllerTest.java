package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.controller.TestContainerConfig;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.CategoryRepository;
import com.e_com.e_com_spring.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest extends TestContainerConfig {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserUtils userUtils;

    private RegisterPostDto adminRegister;
    private RegisterPostDto customerRegister;
    private HttpHeaders adminHeaders;
    private HttpHeaders customerHeaders;
    private User admin;
    private User customer;

    @BeforeEach
    void setup(){
        adminRegister = userUtils.createRegisterPostDto(
                "Admin",
                "BADJI",
                "admin@gmail.com",
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
        customerHeaders = userUtils.getHttpHeaders(customer.getEmail());
    }

    @AfterEach
    void tearDown(){
        adminRegister = null;
        admin = null;
        customerRegister = null;
        customer = null;
        adminHeaders = null;
        customerHeaders = null;
    }

    @Nested
    class CreateTests{
        @Test
        void shouldCreateCategory() throws Exception{
            CategoryPostDto categoryPostDto = CategoryPostDto.builder()
                    .name("Category 1")
                    .build();
            mockMvc.perform(
                    post("/categories")
                            .content(objectMapper.writeValueAsString(categoryPostDto))
                    .contentType(MediaType.APPLICATION_JSON)
                            .headers(adminHeaders)
            )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("Category 1"));

        }

        @Test
        void shouldNotCreateCategory_IfNotAdmin() throws Exception{
            CategoryPostDto categoryPostDto = CategoryPostDto.builder()
                    .name("Category 1")
                    .build();
            mockMvc.perform(
                            post("/categories")
                                    .content(objectMapper.writeValueAsString(categoryPostDto))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .headers(customerHeaders)
                    )
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.error").value("Access Denied"));
        }
    }
}