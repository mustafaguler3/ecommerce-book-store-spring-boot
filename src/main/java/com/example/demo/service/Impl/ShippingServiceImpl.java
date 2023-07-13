package com.example.demo.service.Impl;

import com.example.demo.domain.UserShipping;
import com.example.demo.repository.ShippingRepository;
import com.example.demo.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public UserShipping findById(Long id) {
        return shippingRepository.getOne(id);
    }

    @Override
    public void removeById(Long id) {
        shippingRepository.deleteById(id);
    }
}
