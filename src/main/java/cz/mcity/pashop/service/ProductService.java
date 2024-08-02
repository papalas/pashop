package cz.mcity.pashop.service;

import cz.mcity.pashop.controller.ProductsDto;
import cz.mcity.pashop.model.Products;
import cz.mcity.pashop.model.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;

    @Autowired
    public  ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductsDto> getAllProducts() {
        return  productsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductsDto convertToDTO(Products products) {
        return new ProductsDto(products.getId(),
                products.getName(),
                products.getDescription(),
                products.getPrice(),
                products.getStockQuantity(),
                products.getAltDesc());
    }

}
