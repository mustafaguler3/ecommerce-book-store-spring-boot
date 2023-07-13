package com.example.demo.repository;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
