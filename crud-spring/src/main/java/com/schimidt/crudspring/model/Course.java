package com.schimidt.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SQLSelect;
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

    @Column(name="category",length = 20,nullable = false)
    @NotNull
    @Length(max = 50)
    @Pattern(regexp = "Back-end|Front-end")
    private String category;

    @Column(name="status",length = 10,nullable = false)
    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Ativo|Inativo")
    private String status = "Ativo";
}
