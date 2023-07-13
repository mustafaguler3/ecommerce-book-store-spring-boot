package com.example.demo.service.Impl;

import com.example.demo.domain.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment findById(Long id) {
        return paymentRepository.getOne(id);
    }

    @Override
    public void removeById(Long creditCardId) {
        paymentRepository.deleteById(creditCardId);
    }
}



















