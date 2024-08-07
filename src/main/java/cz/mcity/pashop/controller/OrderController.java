package cz.mcity.pashop.controller;

import cz.mcity.pashop.exception.ProductNotFoundException;
import cz.mcity.pashop.model.User;
import cz.mcity.pashop.service.CustomUserDetailsService;
import cz.mcity.pashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class OrderController {
    private final OrderService orderService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public OrderController(OrderService orderService, CustomUserDetailsService userDetailsService) {
        this.orderService = orderService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/addItemToBasket")
    public ResponseEntity<String> addItemToBasket(@RequestBody OrderItemDTO dto, Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow( () -> new UsernameNotFoundException("User not found"));
            orderService.addItemToBasket(user, dto.productId(), dto.quantity());
            return ResponseEntity.ok("Item added to basket successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }

    }

    @PostMapping("/removeItemFromBasket")
    public ResponseEntity<String> removeItemFromBasket(@RequestBody OrderItemDTO dto, Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow( () -> new UsernameNotFoundException("User not found"));
            orderService.removeItemFromBasket(user, dto.productId(), dto.quantity());
            return ResponseEntity.ok("Item removed from basket successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}