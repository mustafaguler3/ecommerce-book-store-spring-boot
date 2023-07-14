package com.example.demo.service.Impl;

import com.example.demo.domain.BillingAddress;
import com.example.demo.domain.UserBilling;
import com.example.demo.service.BillingAddressService;
import org.springframework.stereotype.Service;

@Service
public class BillingAddressServiceImpl implements BillingAddressService {
    @Override
    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
        billingAddress.setBillingAddressName(userBilling.getUserBillingName());
        billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
        billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
        billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
        billingAddress.setBillingAddressState(userBilling.getUserBillingState());
        billingAddress.setBillingAddressZipCode(userBilling.getUserBillingZipCode());
        billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());

        return billingAddress;
    }
}
