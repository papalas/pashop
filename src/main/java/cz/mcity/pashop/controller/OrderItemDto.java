package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemDto(
        Long id,
        // Long orderId,
        ProductDTO product,
        Integer quantity,
        @NotNull(message = "cannot be null") @Min(message = "not negaive", value = 0) BigDecimal priceAtTime,
        Integer daysToDeliver
) implements Serializable {

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        ProductDTO productDTO = ProductDTO.fromEntity(orderItem.getProduct());
        Integer daysToDeliver = 0;
        if (orderItem.getQuantity()>productDTO.stockQuantity())  daysToDeliver = productDTO.deliveryDays();
        return new OrderItemDto(orderItem.getId(),
                productDTO,
                orderItem.getQuantity(),
                orderItem.getPriceAtTime(),
                daysToDeliver
        );
    }
}