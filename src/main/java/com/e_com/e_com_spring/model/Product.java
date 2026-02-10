package com.e_com.e_com_spring.model;

import com.e_com.e_com_spring.model.auditing.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(
            name = "product_seq_generator",
            sequenceName = "product_seq",
            initialValue = 1000,
            allocationSize = 20
    )
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(optional = false)
    private Category category;
}
