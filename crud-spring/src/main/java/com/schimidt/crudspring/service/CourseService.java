package com.schimidt.crudspring.service;

import com.schimidt.crudspring.dto.CourseDTO;
import com.schimidt.crudspring.dto.CoursePageDTO;
import com.schimidt.crudspring.dto.mapper.CourseMapper;
import com.schimidt.crudspring.exception.RecordNotFoundException;
import com.schimidt.crudspring.model.Course;
import com.schimidt.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper courseMapper;


    public CourseService(
            CourseRepository courseRepository,
            CourseMapper mapper) {
        this.repository = courseRepository;
        this.courseMapper = mapper;

    }

    public CoursePageDTO list(@PositiveOrZero int pageNum, @Max(100) int size) {
        Page<Course> page = repository.findAll(PageRequest.of(pageNum, size));
        List<CourseDTO> courses = page.get().map(courseMapper::toDTO).collect(Collectors.toList());
        return new CoursePageDTO(courses, page.getTotalElements(), page.getTotalPages());

    }

//    public List<CourseDTO> list() {
//        return repository.findAll()
//                .stream()
//                .map(mapper::toDTO)
//                .collect(Collectors.toList());
//    }

    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return repository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO courseDTO) {
        return repository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategory(courseDTO.category()));
                    // recordFound.setLessons(course.getLessons());
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(recordFound.getLessons()::add);
                    return courseMapper.toDTO(repository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {

        repository.delete(repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
