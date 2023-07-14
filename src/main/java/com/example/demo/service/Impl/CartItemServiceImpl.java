package com.example.demo.service.Impl;

import com.example.demo.domain.*;
import com.example.demo.repository.BookToCartItemRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private BookToCartItemRepository bookToCartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice()).multiply(new BigDecimal(cartItem.getQyt()));
        bigDecimal = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        cartItem.setSubTotal(bigDecimal);

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    @Override
    public CartItem addBookToCartItem(Book book, User user, int qyt) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList){
            if (book.getId() == cartItem.getBook().getId()){
                cartItem.setQyt(cartItem.getQyt()+qyt);
                cartItem.setSubTotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qyt)));

                cartItemRepository.save(cartItem);

                return cartItem;
            }
            CartItem cartItems = new CartItem();
            cartItem.setShoppingCart(user.getShoppingCart());
            cartItem.setBook(book);
            cartItem.setQyt(qyt);
            cartItem.setSubTotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qyt)));
            cartItem = cartItemRepository.save(cartItems);

            BookToCartItem bookToCartItem = new BookToCartItem();
            bookToCartItem.setBook(book);
            bookToCartItem.setCartItem(cartItems);

            bookToCartItemRepository.save(bookToCartItem);

            return cartItem;
        }

        return null;
    }

    @Override
    public CartItem findById(Long cartItemId) {
        return cartItemRepository.getOne(cartItemId);
    }

    @Override
    public void removeCartItem(CartItem cartItemId) {
        bookToCartItemRepository.deleteByCartItem(cartItemId);
        cartItemRepository.delete(cartItemId);
    }
}























