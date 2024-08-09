package cz.mcity.pashop.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndStatus(User user, OrderStatus status);
    List<Order> findByUserAndStatusIn(User user, List<OrderStatus> status);
    List<Order> findByStatus(OrderStatus orderStatus);
}