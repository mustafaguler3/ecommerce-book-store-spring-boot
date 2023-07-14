package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.ShoppingCart;
import com.example.demo.domain.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addBookToCartItem(Book book, User user, int qyt);

    CartItem findById(Long cartItemId);

    void removeCartItem(CartItem cartItemId);
}













