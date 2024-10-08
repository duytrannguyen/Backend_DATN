package com.poly.Mapper;

import com.poly.Model.Product;
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
        dto.setYearManufacture(product.getYearManufacture());
        dto.setSize(product.getSize());
        dto.setMaterial(product.getMaterial());
        dto.setDescription(product.getDescription());
        dto.setPlaceProduction(product.getPlaceProduction());
        dto.setPostingDate(product.getPostingDate());
        dto.setQuantity(product.getQuantity());
        dto.setPercentDecrease(product.getPercentDecrease());
        dto.setPriceDecreased(product.getPriceDecreased());
        
        // Chuyển đổi các thuộc tính liên quan nếu cần
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getCategoryName());
        }
        if (product.getImageId() != null) {
            dto.setImageUrl(product.getImageId().getImageName());
        }
        if (product.getStatus() != null) {
            dto.setStatusName(product.getStatus().getStatusName());
        }
        if (product.getSeller() != null) {
            dto.setSellerName(product.getSeller().getShopName());
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
        product.setYearManufacture(dto.getYearManufacture());
        product.setSize(dto.getSize());
        product.setMaterial(dto.getMaterial());
        product.setDescription(dto.getDescription());
        product.setPlaceProduction(dto.getPlaceProduction());
        product.setPostingDate(dto.getPostingDate());
        product.setQuantity(dto.getQuantity());
        product.setPercentDecrease(dto.getPercentDecrease());
        product.setPriceDecreased(dto.getPriceDecreased());
        
        // Đừng quên thiết lập các thuộc tính nhiều đối tượng nếu cần thiết
        // product.setCategory(...);
        // product.setImageId(...);
        // product.setStatus(...);
        // product.setSeller(...);
        
        return product;
    }
}

