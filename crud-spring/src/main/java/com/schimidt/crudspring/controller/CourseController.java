package com.schimidt.crudspring.controller;

import com.schimidt.crudspring.model.Course;
import com.schimidt.crudspring.repository.CourseRepository;
import com.schimidt.crudspring.service.CourseService;
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
public class CourseController {

    private final CourseService service;

    public CourseController( CourseService service){
        this.service = service;
    }

    @GetMapping
    public @ResponseBody List<Course> list(){
        return service.list();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course){
        return service.create(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id){
       return service.findById(id)
               .map(record -> ResponseEntity.ok().body(record))
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update( @PathVariable @NotNull @Positive Long id,
                                          @RequestBody @Valid Course course){
      return  service.update(id,course)
              .map(recordFound -> ResponseEntity.ok().body(recordFound))
              .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
              if(service.delete(id)){
                  return ResponseEntity.noContent().<Void>build();
              }
              return  ResponseEntity.notFound().build();
    }
}
