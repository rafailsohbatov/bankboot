package az.mycompany.bankboot.dto.request;

import az.mycompany.bankboot.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ReqTransaction {

    @JsonProperty(value = "transactionId")
    private Long id;
    private String crAccount;
    private Double amount;
    private String currency;
    private Long accountId;
}
