package com.example.demo.service;

import com.example.demo.domain.UserShipping;

public interface ShippingService {
    UserShipping findById(Long id);
    void removeById(Long id);
}
