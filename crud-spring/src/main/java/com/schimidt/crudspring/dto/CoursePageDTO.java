package com.schimidt.crudspring.dto;

import java.util.List;

public record CoursePageDTO(List<CourseDTO> courseDTOS,long totalElements, int totalPages) {
}
