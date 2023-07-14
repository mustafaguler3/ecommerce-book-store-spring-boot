package com.example.demo.service;

import com.example.demo.domain.ShippingAddress;
import com.example.demo.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
