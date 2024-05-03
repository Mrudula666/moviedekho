package com.moviedekho.PaymentService.controller;

import com.moviedekho.PaymentService.entity.Payment;
import com.moviedekho.PaymentService.model.PaymentRequest;
import com.moviedekho.PaymentService.model.PaymentResponse;
import com.moviedekho.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PaymentController {


    @Autowired
    private PaymentService paymentService;


    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setUsername(paymentRequest.getUsername());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(new Date());
         payment.setSubscriptionPlan(paymentRequest.getSubscriptionPlan());

        PaymentResponse paymentResponse = paymentService.addPayment(payment);

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
