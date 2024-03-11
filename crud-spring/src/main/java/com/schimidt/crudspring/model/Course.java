package com.schimidt.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schimidt.crudspring.enums.Category;
import com.schimidt.crudspring.enums.Status;
import com.schimidt.crudspring.enums.converters.CategoryConverter;
import com.schimidt.crudspring.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Entity
@SQLDelete(sql = "UPDATE course SET status = 'Inativo' WHERE id=?")
@SQLRestriction("status <> 'Inativo'")
//@Table(name="cursos") caso ja exista a tabela no banco, declarar o nome da tabela
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotNull
    @NotBlank
    @Length(min = 5, max = 100)
    private String name;

    @Column(name = "category", length = 10, nullable = false)
    @NotNull
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "status", length = 10, nullable = false)
    @NotNull
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    //@JoinColumn(name = "course_id")
    private List<Lesson> lessons = new ArrayList<>();


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", lessons=" + lessons +
                '}';
    }

    public Course(Long id, String name, Category category, Status status, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.lessons = lessons;
    }

    public Course() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
