package eu.avidatech.util;

import eu.avidatech.config.ApplicationConfig;
import eu.avidatech.payload.Payment;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final ApplicationConfig applicationConfig;


    private static final Logger LOG = LogManager.getLogger(JwtUtil.class);

    private Map<String, Object> preparePaymentInstructionPayload(Payment payment) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", payment.getAmount());
        payload.put("currency", "EUR");
        payload.put("access_key", applicationConfig.getAccesskey());
        payload.put("merchant_reference", UUID.randomUUID().toString());
        payload.put("merchant_return_url", applicationConfig.getMerchantReturnUrl());
        payload.put("merchant_notification_url", applicationConfig.getMerchantNotificationUrl());
        payload.put("checkout_email", payment.getCheckoutEmail());
        payload.put("checkout_first_name", payment.getCheckoutFirstname());
        return payload;
    }

    public String generateToken(Payment payment) {
        Key key = new SecretKeySpec(applicationConfig.getSecretkey().getBytes(), "AES");
        return Jwts.builder()
                .setClaims(preparePaymentInstructionPayload(payment))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()))
                .setHeaderParam("typ", Header.JWT_TYPE)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String generateToken(String accessKey) {
        Key key = new SecretKeySpec(applicationConfig.getSecretkey().getBytes(), "AES");
        return Jwts.builder()
                .claim("access_key", accessKey)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()))
                .setHeaderParam("typ", Header.JWT_TYPE)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        Key key = new SecretKeySpec(applicationConfig.getSecretkey().getBytes(), "AES");
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
