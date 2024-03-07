package com.schimidt.crudspring.service;

import com.schimidt.crudspring.dto.CourseDTO;
import com.schimidt.crudspring.dto.mapper.CourseMapper;
import com.schimidt.crudspring.exception.RecordNotFoundException;
import com.schimidt.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;


    public CourseService(
            CourseRepository courseRepository,
            CourseMapper mapper) {
        this.repository = courseRepository;
        this.mapper = mapper;

    }

    public List<CourseDTO> list() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return mapper.toDTO(repository.save(mapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO course) {
        return repository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.name());
                    recordFound.setCategory(mapper.convertCategory(course.category()));
                    return mapper.toDTO(repository.save(recordFound));
                }).orElseThrow(()-> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {

        repository.delete(repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
