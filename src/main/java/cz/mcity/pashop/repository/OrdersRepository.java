package cz.mcity.pashop.repository;

import cz.mcity.pashop.model.Orders;
import cz.mcity.pashop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(Users user);
}