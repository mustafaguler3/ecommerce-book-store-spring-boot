package com.example.demo.service;

import com.example.demo.domain.Payment;

public interface PaymentService {
    Payment findById(Long id);

    void removeById(Long creditCardId);
    //userPayment - Payment
    Payment setByUserPayment(Payment userPayment, Payment payment);
}
