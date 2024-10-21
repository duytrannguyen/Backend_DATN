package com.poly.Mapper;

import com.poly.Model.Product;
import com.poly.Model.Category; // Import model Category
import com.poly.Model.Image; // Import model Image
import com.poly.Model.Seller; // Import model Seller
import com.poly.dto.ProductDTO;

public class ProductMapper {

    // Chuyển đổi từ Product sang ProductDTO
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setSize(product.getSize());
        dto.setMaterial(product.getMaterial());
        dto.setDescription(product.getDescription());
        dto.setPlaceProduction(product.getPlaceProduction());
        dto.setPostingDate(product.getPostingDate());
        dto.setQuantity(product.getQuantity());

        // Chuyển đổi các thuộc tính liên quan nếu cần
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getCategoryName());
        }
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
        }
        // if (product.getImageId() != null) {
        // dto.setImageUrl(product.getImageId().getImageName());
        // }
        if (product.getStatus() != null) {
            dto.setStatusName(product.getStatus());
        }
        if (product.getSeller() != null) {
            dto.setSellerId(product.getSeller().getSellerId());
        }

        return dto;
    }

    // Chuyển đổi từ ProductDTO sang Product (nếu cần)
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setSize(dto.getSize());
        product.setMaterial(dto.getMaterial());
        product.setDescription(dto.getDescription());
        product.setPlaceProduction(dto.getPlaceProduction());
        product.setPostingDate(dto.getPostingDate());
        product.setQuantity(dto.getQuantity());

        // Thiết lập các thuộc tính nhiều đối tượng nếu cần thiết
        if (dto.getCategoryId() > 0) {
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId()); // Giả sử bạn có thuộc tính categoryId trong ProductDTO
            product.setCategory(category);
        }
        // if (dto.getImageUrl() != null) {
        // Image image = new Image();
        // image.setImageName(dto.getImageUrl()); // Giả sử bạn có thuộc tính imageId
        // trong ProductDTO
        // product.setImageId(image);
        // }
        // Thiết lập trạng thái nếu cần, nếu trạng thái được quản lý bởi một đối tượng
        // riêng
        // product.setStatus(dto.getStatus());

        if (dto.getSellerId() > 0) {
            Seller seller = new Seller();
            seller.setSellerId(dto.getSellerId()); // Giả sử bạn có thuộc tính sellerId trong ProductDTO
            product.setSeller(seller);
        }

        return product;
    }
}
