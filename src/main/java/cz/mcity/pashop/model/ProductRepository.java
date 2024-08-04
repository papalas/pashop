package cz.mcity.pashop.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {

        Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name,String description, Pageable pageable);
}
