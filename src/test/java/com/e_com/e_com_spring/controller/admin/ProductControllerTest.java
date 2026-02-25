package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.controller.TestContainerConfig;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.service.admin.category.ICategoryService;
import com.e_com.e_com_spring.service.admin.product.IProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest extends TestContainerConfig {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProductService productService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    private RegisterPostDto adminRegister;
    private RegisterPostDto customerRegister;
    private HttpHeaders adminHeaders;
    private HttpHeaders customerHeaders;
    private User admin;
    private User customer;
    private ProductPostDto productPostDto;

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
        userRepository.deleteAll();
    }

    @Nested
    class CreateTests{
        @Test
        void admin_can_create_a_product() throws Exception{
            CategoryGetDto categoryGetDto = categoryService.create(createCategoryPostDTO("Category "));
            productPostDto = createProductPostDTO("Product 1", categoryGetDto.getId());
            mockMvc.perform(
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(adminHeaders)
                            .content(objectMapper.writeValueAsString(productPostDto))
            )
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").isNotEmpty())
                    .andExpect(jsonPath("$.category.id").value(categoryGetDto.getId()));
        }

        @Test
        void not_admin_cannot_create_a_product() throws Exception{
            CategoryGetDto categoryGetDto = categoryService.create(createCategoryPostDTO("Category 2"));
            ProductPostDto productPostDto = createProductPostDTO("Product 2",categoryGetDto.getId());
            mockMvc.perform(
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(customerHeaders)
                            .content(objectMapper.writeValueAsString(productPostDto))
                    )
                    .andExpect(jsonPath("$.error").value("Forbidden"))
                    .andExpect(jsonPath("$.message").value("You are not allowed to perform this action"))
                    .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()));
        }
    }

    private CategoryPostDto createCategoryPostDTO(String name){
        return CategoryPostDto.builder()
                .name(name)
                .build();
    }

    private ProductPostDto createProductPostDTO(String productName, Long categoryId){
        return ProductPostDto.builder()
                .name(productName)
                .categoryId(categoryId)
                .build();
    }
}