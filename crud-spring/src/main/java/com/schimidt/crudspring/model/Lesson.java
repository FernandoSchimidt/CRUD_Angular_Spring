package com.schimidt.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String youtubeUrl;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "course_id",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;

}
