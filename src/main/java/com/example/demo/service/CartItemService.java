package com.example.demo.service;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.ShoppingCart;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
