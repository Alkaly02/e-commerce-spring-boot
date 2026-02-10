package com.e_com.e_com_spring.repository;

import com.e_com.e_com_spring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
