package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
@Schema(description = "Product Data Transfer Object")
public record ProductDTO(Integer id, String name, String description, BigDecimal price, Integer stockQuantity,
                         String altDesc, Integer deliveryDays) implements Serializable {

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "price = " + price + ", " +
                "stockQuantity = " + stockQuantity + ", " +
                "altDesc = " + altDesc +
                "deliveryDays = " + deliveryDays +
                ")";
    }

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getStockQuantity(),product.getAltDesc(),product.getDeliveryDays());
    }
}