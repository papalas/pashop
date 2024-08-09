package cz.mcity.pashop.controller;

import cz.mcity.pashop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Get list of all products", description = "Returns list of all products, paged, searchable")
    public PageDTO<ProductDTO> listProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {

        Pageable pageable = PageRequest.of(page, perPage);
        return productService.findProducts(search, pageable);
    }

    @Operation(summary = "Get a product by its ID")
    @ApiResponse(responseCode = "200", description = "Found the product",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))})
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/listProducts/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product to be retrieved") @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}