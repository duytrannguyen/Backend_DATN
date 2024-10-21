package com.poly.Mapper;

import com.poly.Model.Image;
import com.poly.dto.ImageDTO;

public class ImageMapper {
    public static ImageDTO toDTO(Image image) {
        return new ImageDTO(image.getImageId(), image.getImageName(), image.getProductId());
    }
}
