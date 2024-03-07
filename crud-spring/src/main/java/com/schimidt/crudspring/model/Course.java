package com.schimidt.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schimidt.crudspring.enums.Category;
import com.schimidt.crudspring.enums.Status;
import com.schimidt.crudspring.enums.converters.CategoryConverter;
import com.schimidt.crudspring.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@SQLDelete(sql = "UPDATE course SET status = 'Inativo' WHERE id=?")
@SQLRestriction("status <> 'Inativo'")
//@Table(name="cursos") caso ja exista a tabela no banco, declarar o nome da tabela
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column(name = "name",length = 100,nullable = false)
    @NotNull
    @NotBlank
    @Length(min = 5,max = 100)
    private String name;

    @Column(name="category", length = 10,nullable = false)
    @NotNull
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(name="status",length = 10,nullable = false)
    @NotNull
    @Convert(converter = StatusConverter.class)
    @Length(max = 10)
    private Status status;
}
