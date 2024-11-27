package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data               // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Entity
@Table              // Since not providing name, it will be class name. E.g. HeyMan => hey_man
public class Employee {
    @Id                     // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // Auto generate. If we delete a record, its ID is not used again
    private Long id;
    private String name;
    private String email;
}
