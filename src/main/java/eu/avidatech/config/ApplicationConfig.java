package eu.avidatech.config;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

@Data
@Configuration
public class ApplicationConfig {

    @Value("${header.key}")
    private String headerKey;
    @Value("${montonio.secret-key}")
    private String secretkey;
    @Value("${montonio.access-key}")
    private String accesskey;
    @Value("${payment.status.success}")
    private String successPayment;
    @Value("${payment.status.failed}")
    private String invalidPayment;

    @Bean
    public Logger logger() {
        return LogManager.getLogger("montonio");
    }

    @Bean
    public RestTemplate restTemplate() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(asList(APPLICATION_JSON, APPLICATION_OCTET_STREAM));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

}
