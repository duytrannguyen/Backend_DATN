package com.poly.Mapper;

import com.poly.Model.Category;
import com.poly.dto.CategoryDTO;

public class CategoryMapper {

    // Chuyển đổi từ Category sang CategoryDTO
    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setStatusName(category.getStatusName());
        dto.setCategoryName(category.getCategoryName());

        return dto;
    }

    // Chuyển đổi từ CategoryDTO sang Category
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        category.setStatusName(dto.getStatusName());
        category.setCategoryName(dto.getCategoryName());

        return category;
    }
}
