package cz.mcity.pashop;

import cz.mcity.pashop.model.Product;
import cz.mcity.pashop.model.ProductRepository;
import cz.mcity.pashop.model.User;
import cz.mcity.pashop.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {
    private final Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productsRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("user");
                user.setPasswordHash(passwordEncoder.encode("password"));
                user.setEmail("test@mcity.cz");
                userRepository.save(user);
                System.out.println("Sample user created");
            }

            if (productsRepository.count() == 0) {
                createMockProducts();
            }
        };
    }

    private void createMockProducts() {
        List<String> categories = Arrays.asList("Electronics", "Clothing", "Books", "Home & Garden", "Toys");

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName(generateProductName(categories.get(random.nextInt(categories.size()))));
            product.setDescription(generateDescription());
            product.setPrice(generatePrice());
            product.setStockQuantity(random.nextInt(1000));

            productsRepository.save(product);
        }
    }

    private String generateProductName(String category) {
        List<String> adjectives = Arrays.asList("Amazing", "Incredible", "Fantastic", "Wonderful", "Excellent");
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        return adjective + " " + category + " Item " + (random.nextInt(1000) + 1);
    }

    private String generateDescription() {
        List<String> descriptions = Arrays.asList(
                "A must-have item for your collection.",
                "Enhance your lifestyle with this product.",
                "Experience the difference with our latest innovation.",
                "Perfect for everyday use.",
                "Designed for comfort and style."
        );
        return descriptions.get(random.nextInt(descriptions.size()));
    }

    private BigDecimal generatePrice() {
        return BigDecimal.valueOf(10 + (1000 - 10) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
    }



}