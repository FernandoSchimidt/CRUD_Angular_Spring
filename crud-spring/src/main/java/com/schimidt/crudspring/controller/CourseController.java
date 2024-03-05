package com.schimidt.crudspring.controller;

import com.schimidt.crudspring.model.Course;
import com.schimidt.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository repository;

    @GetMapping
    public List<Course> list(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course){
        return repository.save(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id){
       return repository.findById(id)
               .map(record -> ResponseEntity.ok().body(record))
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update( @PathVariable @NotNull @Positive Long id,
                                          @RequestBody @Valid Course course){
       return  repository.findById(id)
               .map(recordFound -> {
                   recordFound.setName(course.getName());
                   recordFound.setCategory(course.getCategory());
                   Course updated = repository.save(recordFound);
                   return  ResponseEntity.ok().body(updated);
               })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
               return repository.findById(id)
                .map(recordFound -> {
                    repository.deleteById(id);
                     return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
