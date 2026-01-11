package com.e_com.e_com_spring.model;

import com.e_com.e_com_spring.model.auditing.Audit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "privileges")
public class Privilege implements Serializable {

    @Serial
    private static final long serialVersionUID = 5899558196335870034L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}
