package cz.mcity.pashop.controller;

import cz.mcity.pashop.exception.BasketEmptyException;
import cz.mcity.pashop.exception.ProductNotFoundException;
import cz.mcity.pashop.model.User;
import cz.mcity.pashop.service.CustomUserDetailsService;
import cz.mcity.pashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> addItemToBasket(@RequestBody BasketItemDto dto, Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
    public ResponseEntity<String> removeItemFromBasket(@RequestBody BasketItemDto dto, Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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

    @GetMapping("/listBasket")
    public ResponseEntity<OrderDto> listBasket(Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<OrderDto> basket = orderService.getBasketDto(user);
            return basket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-Error-Message", e.toString())
                    .build();
        }

    }

    @GetMapping("/listOrders")
    public ResponseEntity<List<OrderDto>> listOrders(Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<OrderDto> orders = orderService.getOrders(user);
            return ResponseEntity.ok(orders);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-Error-Message", e.toString())
                    .build();
        }

    }

    @PostMapping("/payBasket")
    public ResponseEntity<String> payBasket(Authentication auth) {
        try {
            User user = userDetailsService.loadUserByName(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            OrderDto paidBasket = orderService.payBasket( user);
            return ResponseEntity.ok(paidBasket.id().toString());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (BasketEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty or no basket for user");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}