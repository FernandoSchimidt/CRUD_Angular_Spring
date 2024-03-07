package com.schimidt.crudspring.dto.mapper;

import com.schimidt.crudspring.dto.CourseDTO;
import com.schimidt.crudspring.enums.Category;
import com.schimidt.crudspring.enums.Status;
import com.schimidt.crudspring.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), "Front-end");
    }

    public Course toEntity(CourseDTO courseDTO){

        if(courseDTO == null){
            return  null;
        }
        Course course = new Course();
        if(courseDTO.id() != null){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(Category.FRONT_END);
        course.setStatus(Status.ACTIVE);
        return  course;

        //Builder pattern
    }
}
