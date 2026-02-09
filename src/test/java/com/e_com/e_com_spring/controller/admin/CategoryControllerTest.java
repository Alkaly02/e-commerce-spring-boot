package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.controller.TestContainerConfig;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.CategoryRepository;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.util.CategoryUtils;
import com.e_com.e_com_spring.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryUtils categoryUtils;

    private RegisterPostDto adminRegister;
    private RegisterPostDto customerRegister;
    private HttpHeaders adminHeaders;
    private HttpHeaders customerHeaders;
    private User admin;
    private User customer;
    private CategoryGetDto categoryGetDto;

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
        categoryGetDto = categoryUtils.createGetDto("Category 2");
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
        adminRegister = null;
        admin = null;
        customerRegister = null;
        customer = null;
        adminHeaders = null;
        customerHeaders = null;
        categoryGetDto = null;
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
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.error").value("Access Denied"));
        }
    }

    @Nested
    class UpdateTests{
        @Test
        void shouldUpdateCategory() throws Exception{
            CategoryPostDto putDto = CategoryPostDto.builder()
                    .name("Category updated")
                    .build();
            Assertions.assertNotNull(categoryGetDto);
            Assertions.assertNotNull(categoryGetDto.getId());
            mockMvc.perform(
                    put("/categories/{id}", categoryGetDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(putDto))
                            .headers(adminHeaders)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(categoryGetDto.getId()))
                    .andExpect(jsonPath("$.name").value(putDto.getName()));
        }

        @Test
        void shouldNotUpdateCategory_IfNotAdmin() throws Exception{
            CategoryPostDto putDto = CategoryPostDto.builder()
                    .name("Category updated")
                    .build();
            Assertions.assertNotNull(categoryGetDto);
            Assertions.assertNotNull(categoryGetDto.getId());
            mockMvc.perform(
                            put("/categories/{id}", categoryGetDto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(putDto))
                                    .headers(customerHeaders)
                    )
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.error").value("Access Denied"));
        }
    }

}