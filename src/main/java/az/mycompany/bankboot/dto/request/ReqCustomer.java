package az.mycompany.bankboot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReqCustomer {
    @JsonProperty("customerId")
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String phone;
    private Date dob;
    private String cif;
    private String fin;
    private String seria;
    private ReqToken reqToken;
}
