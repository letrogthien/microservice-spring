package com.example.demo.models;


import com.example.demo.type.RoleType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType name ;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}