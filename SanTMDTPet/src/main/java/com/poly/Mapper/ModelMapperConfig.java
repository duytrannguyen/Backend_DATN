package com.poly.Mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.poly.Model.Category;
import com.poly.Model.Product;
import com.poly.dto.CategoryDTO;
import com.poly.dto.ProductDTO;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Cấu hình ánh xạ giữa Product và ProductDTO
		modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {
			@Override
			protected void configure() {
				// Ánh xạ thuộc tính categoryName từ category
				map().setCategoryName(source.getCategory().getCategoryName());
				map().setStatusName(source.getStatus());
//				map().setImageUrl(source.getImageId().getImageName());
			}
		});

		return modelMapper;
	}
}
