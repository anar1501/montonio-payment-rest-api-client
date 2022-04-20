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
}
