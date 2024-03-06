package com.schimidt.crudspring.service;

import com.schimidt.crudspring.model.Course;
import com.schimidt.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;


    public CourseService(CourseRepository courseRepository){
        this.repository= courseRepository;

    }

    public List<Course> list(){
        return repository.findAll();
    }

    public Optional<Course> findById(@PathVariable @NotNull @Positive Long id){
        return repository.findById(id);
    }
    public Course create(@Valid Course course){
        return repository.save(course);
    }

   public Optional<Course> update (@NotNull @Positive Long id, @Valid Course course){
        return repository.findById(id)
                .map(recordFound->{
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    return  repository.save(recordFound);
                });
   }

    public boolean delete(@PathVariable @NotNull @Positive Long id){
        return repository.findById(id)
                .map(recordFound -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
