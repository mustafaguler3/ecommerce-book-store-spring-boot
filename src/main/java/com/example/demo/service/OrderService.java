package com.example.demo.service;

import com.example.demo.domain.*;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress, Payment payment,String shippingMethod, User user);

    Order findOne(long id);
}
