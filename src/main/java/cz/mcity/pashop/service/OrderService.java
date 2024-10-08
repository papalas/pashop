package cz.mcity.pashop.service;

import cz.mcity.pashop.controller.OrderDto;
import cz.mcity.pashop.exception.BasketEmptyException;
import cz.mcity.pashop.exception.ProductNotFoundException;
import cz.mcity.pashop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addItemToBasket(User user, Long productId, int quantity) {
        Order basket = getOrCreateBasket(user);
        Product product = getProduct(productId);

        OrderItem item = findOrCreateOrderItem(basket, product);
        item.setQuantity(item.getQuantity() + quantity);

        basket.recalculateTotal();
        basket.recalculateDaysToDeliver();
        orderRepository.save(basket);
    }

    @Transactional
    public void removeItemFromBasket(User user, Long productId, int quantity) {
        Order basket = getBasket(user).orElseThrow(() -> new RuntimeException("Basket not found for user"));
        OrderItem item = findOrderItem(basket, productId).orElseThrow(() -> new RuntimeException("Product " + productId +  " not found for user"));

        if (item.getQuantity() <= quantity) {
            basket.getOrderItems().remove(item);
        } else {
            item.setQuantity(item.getQuantity() - quantity);
        }

        basket.recalculateTotal();
        basket.recalculateDaysToDeliver();
        orderRepository.save(basket);
    }



    private Order getOrCreateBasket(User user) {
        return getBasket(user)
                .orElseGet(() -> createNewBasket(user));
    }

    private Optional<Order> getBasket(User user) {
        return orderRepository.findByUserAndStatus(user, OrderStatus.BASKET);
    }

    public Optional<OrderDto> getBasketDto(User user) {
        Optional<Order> basket = getBasket(user);
        return basket.map(OrderDto::fromEntity);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    private Order createNewBasket(User user) {
        Order order = new Order();
        order.setStatus(OrderStatus.BASKET);
        order.setUser(user);
        order.recalculateTotal();
        order.recalculateDaysToDeliver();
        orderRepository.save(order);
        return order;
    }

    private OrderItem findOrCreateOrderItem(Order order, Product product) {
        return order.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setOrder(order);
                    newItem.setProduct(product);
                    newItem.setPriceAtTime(product.getPrice());
                    newItem.setQuantity(0);
                    order.getOrderItems().add(newItem);
                    return newItem;
                });
    }

    private Optional<OrderItem> findOrderItem(Order order, Long productId) {
        return order.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    public List<OrderDto> getOrders(User user) {
        List<OrderStatus> orderStatuses= Arrays.asList(OrderStatus.ORDERED, OrderStatus.DELIVERED);
        return orderRepository.findByUserAndStatusIn(user,orderStatuses)
                .stream()
                .map(OrderDto::fromEntity)
                .toList();
    }

    @Transactional
    public OrderDto payBasket(User user) {
        Order basket =  getBasket(user).orElseThrow(()-> new BasketEmptyException("Basket is empty") );
        basket.recalculateDaysToDeliver();

        for (OrderItem orderItem : basket.getOrderItems()) {
            Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int newStockQuantity = Math.max(0, product.getStockQuantity() - orderedQuantity);
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
        }
        basket.setStatus(OrderStatus.ORDERED);
        basket.setOrderDate(LocalDateTime.now());
        orderRepository.save(basket);
        return OrderDto.fromEntity(basket);
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateOrdersToDelivered() {
        List<Order> orderedOrders = orderRepository.findByStatus(OrderStatus.ORDERED);
        for (Order order : orderedOrders) {
            if (order.getDaysToDeliver() == 0 && order.getDeliveryDate() == null) {
                order.setStatus(OrderStatus.DELIVERED);
                order.setDeliveryDate(LocalDateTime.now());
                System.out.println("Order " + order.getId() + " has been delivered.");
            } else {
                order.setDaysToDeliver(order.getDaysToDeliver() - 1);
                System.out.println("Order " + order.getId() + " has " + order.getDaysToDeliver() + " days left to deliver.");
            }
        }
        orderRepository.saveAll(orderedOrders);

    }
}
