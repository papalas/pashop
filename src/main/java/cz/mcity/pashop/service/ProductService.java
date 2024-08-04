package cz.mcity.pashop.service;

import cz.mcity.pashop.controller.PageDTO;
import cz.mcity.pashop.controller.ProductDTO;
import cz.mcity.pashop.model.Product;
import cz.mcity.pashop.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productsRepository;

    @Autowired
    public  ProductService(ProductRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public PageDTO<ProductDTO> findProducts(String search, Pageable pageable) {
        Page<Product> productPage;

        if (search != null && !search.isEmpty()) {
            productPage =  productsRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search,search, pageable);
        } else {
            productPage=  productsRepository.findAll(pageable);
        }

        List<ProductDTO> productDTOs = productPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageDTO<ProductDTO>(
                productDTOs,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getAltDesc(),
                product.getDeliveryDays());
    }

}
