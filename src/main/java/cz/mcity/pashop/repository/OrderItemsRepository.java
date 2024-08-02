package cz.mcity.pashop.repository;

import cz.mcity.pashop.model.OrderItems;
import cz.mcity.pashop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrder(Orders order);
}