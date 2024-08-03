package cz.mcity.pashop.controller;

import cz.mcity.pashop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/public")
@Tag(name="Public",description="Public API")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/listProducts")
    @Operation(summary = "Get list of all products", description = "Returns list of all products, paged")
    public List<ProductsDto> listProducts() {
        return productService.getAllProducts();
    }
}