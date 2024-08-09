package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.Order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link cz.mcity.pashop.model.Order}
 */
public record OrderDto(
        Long id,
        UserDto user,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        BigDecimal totalAmount,
        String status,
        List<OrderItemDto> orderItems,
        Integer daysToDeliver
) implements Serializable {

    public static OrderDto fromEntity(Order order) {
        UserDto userDto = UserDto.fromEntity(order.getUser());
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(OrderItemDto::fromEntity)
                .toList();
        return new OrderDto(order.getId()
                ,userDto,
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                orderItemDtos,
                order.getDaysToDeliver());
    }
}