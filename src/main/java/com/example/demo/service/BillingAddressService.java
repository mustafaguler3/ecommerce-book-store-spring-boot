package com.example.demo.service;

import com.example.demo.domain.BillingAddress;
import com.example.demo.domain.UserBilling;

public interface BillingAddressService {
    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
