package cz.mcity.pashop.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "OrderItem Object")
public record OrderItemDTO(
     Long productId,
     int quantity
) implements Serializable {

}

