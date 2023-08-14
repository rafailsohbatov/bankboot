package az.mycompany.bankboot.dto.request;

import az.mycompany.bankboot.dto.response.RespCustomer;
import lombok.Data;

@Data
public class ReqAccount {

    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private Long customerId;
}
