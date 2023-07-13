package com.example.demo.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal grandTotal;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}





















