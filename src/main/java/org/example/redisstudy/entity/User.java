package org.example.redisstudy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean isUse;
    private boolean isDeleted;
}
