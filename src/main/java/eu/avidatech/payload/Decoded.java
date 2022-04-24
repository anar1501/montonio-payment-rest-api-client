package eu.avidatech.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Decoded {
    private String amount;
    @JsonProperty("access_key")
    private String accessKey;
    @JsonProperty("merchant_reference")
    private String merchantReference;
    private String status;
    @JsonProperty("payment_uuid")
    private String paymentUUID;
    @JsonProperty("customer_iban")
    private String customerIban;
    @JsonProperty("payment_method_name")
    private String paymentMethodName;

}
