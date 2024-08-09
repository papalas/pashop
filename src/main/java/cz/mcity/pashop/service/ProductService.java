package cz.mcity.pashop.service;

import cz.mcity.pashop.controller.PageDTO;
import cz.mcity.pashop.controller.ProductDTO;
import cz.mcity.pashop.model.Product;
import cz.mcity.pashop.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public  ProductService(ProductRepository productsRepository) {
        this.productRepository = productsRepository;
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::fromEntity);
    }


    public PageDTO<ProductDTO> findProducts(String search, Pageable pageable) {
        Page<Product> productPage;

        if (search != null && !search.isEmpty()) {
            productPage =  productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search,search, pageable);
        } else {
            productPage=  productRepository.findAll(pageable);
        }

        List<ProductDTO> productDTOs = productPage.getContent().stream()
                .map(ProductDTO::fromEntity)
                .toList();

        return new PageDTO<>(
                productDTOs,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }


}
