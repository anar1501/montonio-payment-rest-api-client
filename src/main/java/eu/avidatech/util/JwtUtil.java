package eu.avidatech.util;

import eu.avidatech.config.ApplicationConfig;
import eu.avidatech.payload.Decoded;
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

    public Map<String, Object> preparePaymentInstructionPayload(Payment payment) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("amount", payment.getAmount());
        payload.put("currency", "EUR");
        payload.put("checkout_last_name", payment.getCheckoutLastName());
        payload.put("checkout_first_name", payment.getCheckoutFirstname());
        payload.put("checkout_email", payment.getCheckoutEmail());
        payload.put("checkout_phone_number", payment.getCheckoutPhoneNumber());
        payload.put("merchant_reference", payment.getMerchantReference());
        payload.put("merchant_return_url", payment.getMerchantReturnUrl());
        payload.put("merchant_notification_url", payment.getMerchantNotificationUrl());
        payload.put("preselected_locale", payment.getPreselectedLocale());
        payload.put("preselected_aspsp", payment.getPreselectedAspsp());
        payload.put("payment_information_unstructured", payment.getPaymentInformationUnstructured());
        payload.put("payment_information_structured", payment.getPaymentInformationUnstructured());
        payload.put("access_key", applicationConfig.getAccesskey());
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

    public Optional<Decoded> validateJwtTokenV2(String authToken) {
        try {
            Key key = new SecretKeySpec(applicationConfig.getSecretkey().getBytes(), "AES");
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(authToken).getBody();
            return Optional.of(Decoded.builder()
                    .accessKey(claims.get("access_key", String.class))
                    .merchantReference(claims.get("merchant_reference", String.class))
                    .status(claims.get("status", String.class))
                    .build());
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
        return null;
    }

}
