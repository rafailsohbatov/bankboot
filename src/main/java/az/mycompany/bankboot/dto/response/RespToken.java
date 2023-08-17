package az.mycompany.bankboot.dto.response;


import lombok.Data;

@Data
public class RespToken {
    private Long userId;
    private String token;
}
