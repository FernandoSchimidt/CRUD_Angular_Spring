package com.schimidt.crudspring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
//@Table(name="cursos") caso ja exista a tabela no banco, declarar o nome da tabela
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name",length = 200,nullable = false)
    private String name;
    @Column(name="category",length = 20,nullable = false)
    private String category;
}
