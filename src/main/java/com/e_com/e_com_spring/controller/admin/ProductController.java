package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.annotation.CurrentUser;
import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.service.admin.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductGetDto> create(@RequestBody ProductPostDto postDto, @CurrentUser User currentUser){
        return ResponseEntity.ok(productService.create(postDto, currentUser));
    }

    @GetMapping("/:id")
    public ResponseEntity<ProductGetDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/:id")
    public ResponseEntity<ProductGetDto> update(
            @PathVariable Long id,
            @RequestBody ProductPostDto putDto,
            @CurrentUser User currentUser
    ){
        return ResponseEntity.ok(productService.update(id, putDto, currentUser));
    }
}
