package com.moviedekho.PaymentService.service;

import com.moviedekho.PaymentService.entity.Payment;
import com.moviedekho.PaymentService.model.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {


    PaymentResponse addPayment(Payment payment);
}
