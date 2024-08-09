package cz.mcity.pashop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_time", nullable = false)
    private BigDecimal priceAtTime;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public Long getId() {
        return id;
    }

    // Getters and setters
}