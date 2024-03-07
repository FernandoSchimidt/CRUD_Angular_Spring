package com.schimidt.crudspring.enums.converters;

import com.schimidt.crudspring.enums.Category;
import jakarta.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        return null;
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        return null;
    }
}
