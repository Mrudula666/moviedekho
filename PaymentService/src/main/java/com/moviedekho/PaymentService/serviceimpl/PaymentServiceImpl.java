package com.moviedekho.PaymentService.serviceimpl;

import com.moviedekho.PaymentService.entity.Payment;
import com.moviedekho.PaymentService.model.PaymentResponse;
import com.moviedekho.PaymentService.repo.PaymentRepository;
import com.moviedekho.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public PaymentResponse addPayment(Payment payment) {

        paymentRepository.save(payment);
        PaymentResponse response = new PaymentResponse();
        response.setMessage("Payment Done SuccessFully");
        response.setPaymentDetails(payment);
        return response;
    }
}
