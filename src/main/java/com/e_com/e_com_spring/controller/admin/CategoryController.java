package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.service.admin.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryGetDto> create(@RequestBody CategoryPostDto postDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(postDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryGetDto update(@PathVariable Long id,@RequestBody CategoryPostDto putDto){
        return categoryService.update(id, putDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }

    @GetMapping("")
    public List<CategoryGetDto> getAll(){
        return categoryService.getAll();
    }
}
