package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.Products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link Products}
 */
public class ProductsDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stockQuantity;
    private final String altDesc;

    public ProductsDto(Integer id, String name, String description, BigDecimal price, Integer stockQuantity, String altDesc) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.altDesc = altDesc;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getAltDesc() {
        return altDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsDto entity = (ProductsDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.stockQuantity, entity.stockQuantity) &&
                Objects.equals(this.altDesc, entity.altDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, stockQuantity, altDesc);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "price = " + price + ", " +
                "stockQuantity = " + stockQuantity + ", " +
                "altDesc = " + altDesc + ")";
    }
}