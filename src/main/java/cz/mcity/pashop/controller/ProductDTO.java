package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
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
}