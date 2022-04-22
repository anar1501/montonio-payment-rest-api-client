package eu.avidatech.service;


import eu.avidatech.payload.BankList;
import eu.avidatech.payload.Payment;

public interface PaymentService {
    String getRedirectUrl(Payment payload);
    BankList findAllAvailableBank();
    String getPaymentTokenFromTheUrl();
    String validatePayment(String paymentToken);
}
