package cz.mcity.pashop.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "BasketItem Object")
public record BasketItemDto(
        Long productId,
        int quantity
) implements Serializable {

}
