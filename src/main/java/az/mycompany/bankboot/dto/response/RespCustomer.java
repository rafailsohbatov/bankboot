package az.mycompany.bankboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCustomer {
    @JsonProperty(value = "customerId")
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String phone;
    private String dob;
    private String cif;
    private String fin;
    private String seria;
}
