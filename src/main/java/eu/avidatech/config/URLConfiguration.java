package eu.avidatech.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class URLConfiguration {
    @Value("${montonio.payment-url}")
    private String paymentUrl;
    @Value("${montonio.bank-list}")
    private String bankListUrl;
}