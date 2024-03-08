package com.schimidt.crudspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CourseDTO(
        @JsonProperty("id") Long id,
        @NotBlank @NotNull @Length(max = 50) String name,
        @NotNull @Pattern(regexp = "Back-end|Front-end") @Length(max = 50) String category,
        List<LessonDTO> lessons
) {

}
