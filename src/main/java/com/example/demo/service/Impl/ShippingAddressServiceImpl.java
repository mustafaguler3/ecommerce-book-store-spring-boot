package com.example.demo.service.Impl;

import com.example.demo.domain.ShippingAddress;
import com.example.demo.domain.UserShipping;
import com.example.demo.service.ShippingAddressService;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Override
    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
        shippingAddress.setShippingAddressZipCode(userShipping.getUserShippingZipCode());
        shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
        return shippingAddress;
    }
}


















