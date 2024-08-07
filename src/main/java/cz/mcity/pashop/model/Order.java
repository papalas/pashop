package cz.mcity.pashop.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "days_to_deliver", nullable = false)
    private Integer daysToDeliver;

    public Integer getDaysToDeliver() {
        return daysToDeliver;
    }

    public void setDaysToDeliver(Integer daysToDeliver) {
        this.daysToDeliver = daysToDeliver;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }


    public void recalculateTotal() {
        this.totalAmount = orderItems.stream()
                .map(item -> item.getPriceAtTime().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void recalculateDaysToDeliver() {
        this.daysToDeliver = orderItems.stream()
                .mapToInt(item -> {
                    Product product = item.getProduct();
                    if (product.getStockQuantity() >= item.getQuantity()) {
                        return 0;
                    } else {
                        return product.getDeliveryDays();
                    }
                })
                .max()
                .orElse(0);
    }


    // Getters and setters
}