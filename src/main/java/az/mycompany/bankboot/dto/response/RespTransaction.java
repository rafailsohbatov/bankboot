package az.mycompany.bankboot.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespTransaction {

    @JsonProperty(value = "transactionId")
    private Long id;
    private RespAccount dtAccount;
    private String crAccount;
    private Double amount;
    private String currency;
    private RespStatus respStatus;

}
