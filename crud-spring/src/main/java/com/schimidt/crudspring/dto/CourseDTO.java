package com.schimidt.crudspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schimidt.crudspring.enums.Category;
import com.schimidt.crudspring.enums.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CourseDTO(
        @JsonProperty("id") Long id,
        @NotBlank @NotNull @Length(max = 50) String name,
        @NotNull @Length(max = 10) @ValueOfEnum(enumClass = Category.class) String category,
        List<LessonDTO> lessons
) {

}
