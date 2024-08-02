package cz.mcity.pashop.repository;


import cz.mcity.pashop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {

}
