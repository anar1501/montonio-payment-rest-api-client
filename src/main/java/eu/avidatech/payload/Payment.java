package eu.avidatech.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {

    @ApiModelProperty(example = "10", notes = "amount of payment", required = true, position = 1)
    private BigDecimal amount;

    @ApiModelProperty(example = "EUR,USD,TRY,RUB,AZN", notes = "currency of payment", required = true, position = 2)
    private String currency;

    @JsonProperty("checkout_email")
    @ApiModelProperty(example = "test-customer@montonio.com", notes = "checkoutemail of payment", required = true, position = 3)
    private String checkoutEmail;

    @JsonProperty("checkout_first_name")
    @ApiModelProperty(example = "your-checkout-first-name", notes = "checkoutFirstname of payment", required = true, position = 4)
    private String checkoutFirstname;

    @JsonProperty("checkout_last_name")
    @ApiModelProperty(example = "lastname", notes = "checkoutLastName of payment", required = true, position = 5)
    private String checkoutLastName;


    @JsonProperty("preselected_aspsp")
    @ApiModelProperty(example = "LHVBEE22", notes = "preselectedAspsp of payment", required = true, position = 6)
    private String preselectedAspsp;

    @JsonProperty("preselected_locale")
    @ApiModelProperty(example = "et", notes = "preselectedLocale of payment", required = true, position = 7)
    private String preselectedLocale;

    @JsonProperty("checkout_phone_number")
    @ApiModelProperty(example = "your-checkoutPhoneNumber", notes = "checkoutPhoneNumber of payment", required = true, position = 8)
    private String checkoutPhoneNumber;

    @JsonProperty("payment_information_unstructured")
    @ApiModelProperty(example = "Payment for order SO661123", notes = "paymentInformationUnstructured", required = true, position = 9)
    private String paymentInformationUnstructured;

    @JsonProperty("payment_information_structured")
    @ApiModelProperty(example = "", notes = "paymentInformationStructured", required = true, position = 10)
    private String paymentInformationStructured;

    @JsonProperty("merchant_return_url")
    @ApiModelProperty(example = "http://localhost:8080/montonio/payment/thank_you", notes = "merchant-return-url", required = true, position = 11)
    private String merchantReturnUrl;

    @JsonProperty("merchant_notification_url")
    @ApiModelProperty(example = "http://localhost:8080/montonio/payment/payment_webhook", notes = "webhook-notification-url", required = true, position = 12)
    private String merchantNotificationUrl;

    @JsonProperty("merchant_reference")
    @ApiModelProperty(example = "SO661123", notes = "merchantReference must be UUID", required = true, position = 13)
    private String merchantReference;



}
