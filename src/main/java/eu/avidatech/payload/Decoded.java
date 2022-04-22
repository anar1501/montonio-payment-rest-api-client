package eu.avidatech.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Decoded {
    @JsonProperty("access_key")
    private String accessKey;
    @JsonProperty("merchant_reference")
    private String merchantReference;
    @JsonProperty("status")
    private String status;
}
