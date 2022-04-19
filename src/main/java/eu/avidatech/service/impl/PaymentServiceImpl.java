package eu.avidatech.service.impl;


import eu.avidatech.client.RestClient;
import eu.avidatech.config.ApplicationConfig;
import eu.avidatech.config.URLConfiguration;
import eu.avidatech.constant.RegexConstant;
import eu.avidatech.exception.WrongFormatException;
import eu.avidatech.payload.BankList;
import eu.avidatech.payload.Payment;
import eu.avidatech.service.PaymentService;
import eu.avidatech.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import static eu.avidatech.client.HeadersHelper.addHeader;
import static eu.avidatech.enums.MessageCase.*;
import static eu.avidatech.util.StringUtil.parseString;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestClient restClient;
    private final URLConfiguration urlConfiguration;
    private final ApplicationConfig configuration;
    private final JwtUtil jwtUtil;
    private final Logger logger;

    @Override
    public String getRedirectUrl(Payment payment) {
        logger.info(parseString(payment));
        try {
            if (!payment.getCurrency().equals("USD") && !payment.getCurrency().equals("EUR")) {
                throw new WrongFormatException(ONLY_USD_AND_EUR.getMessage());
            } else if (!RegexConstant.validate(payment.getCheckoutEmail())) {
                throw new WrongFormatException(WRONG_FORMAT_EMAIL.getMessage());
            } else if (payment.getCurrency().length() != LEGAL_CURRENCY_SIZE.getLength()) {
                throw new WrongFormatException(WRONG_FORMAT_CURRENCY.getMessage());
            }
        } catch (Exception e) {
            logger.error(parseString(e));
        }
        return urlConfiguration.getPaymentUrl() + jwtUtil.generateToken(payment);
    }

    @Override
    public BankList findAllAvailableBank() {
        return restClient.getForObject(urlConfiguration.getBankListUrl(), new HttpEntity<>(addHeader(configuration.getHeaderKey(), "Bearer " + jwtUtil.generateToken(configuration.getAccesskey()))), BankList.class).getBody();
    }

    @Override
    public String getPaymentTokenFromTheUrl() {
        return restClient.getForObject(urlConfiguration.getBankListUrl(), new HttpEntity<>(addHeader(configuration.getHeaderKey(), "Bearer " + jwtUtil.generateToken(configuration.getAccesskey()))), String.class).getBody();
    }

}


