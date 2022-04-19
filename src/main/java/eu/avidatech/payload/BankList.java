package eu.avidatech.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BankList {

    @JsonProperty("FI")
    private List<CountryName> fi;
    @JsonProperty("LV")
    private List<CountryName> lv;
    @JsonProperty("EE")
    private List<CountryName> ee;
    @JsonProperty("LT")
    private List<CountryName> lt;

    @Data
    @Builder
    public static class CountryName {
        private String bic;
        private String name;
        @JsonProperty("logo_url")
        private String logoUrl;

    }
}
