package com.example.demo.repository;

import com.example.demo.domain.BookToCartItem;
import com.example.demo.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookToCartItemRepository extends JpaRepository<BookToCartItem,Long> {
    void deleteByCartItem(CartItem cartItemId);
}
