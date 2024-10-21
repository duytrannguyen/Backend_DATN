package com.poly.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poly.Model.Image;
import com.poly.Reponsitory.ImageRepository;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Lưu hình ảnh mới vào cơ sở dữ liệu
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    // Tìm kiếm hình ảnh theo productId
    public List<Image> findImagesByProductId(Integer productId) {
        return imageRepository.findByProductId(productId); // Giả sử bạn đã định nghĩa phương thức này trong ImageRepository
    }

    // Xóa hình ảnh theo imageId
    public void deleteImage(Integer imageId) {
        imageRepository.deleteById(imageId); // Giả sử bạn đã định nghĩa phương thức này trong ImageRepository
    }

    // Kiểm tra xem có hình ảnh nào liên quan đến sản phẩm không
    public boolean hasImagesRelatedToProduct(Integer productId) {
        return imageRepository.countByProductId(productId) > 0; // Phương thức đếm số lượng hình ảnh liên quan đến sản phẩm
    }

    // Xóa tất cả hình ảnh liên quan đến sản phẩm
    public void deleteImagesByProductId(Integer productId) {
        List<Image> images = findImagesByProductId(productId);
        if (images != null && !images.isEmpty()) {
            imageRepository.deleteAll(images); // Xóa tất cả hình ảnh liên quan đến sản phẩm
        }
    }
}
