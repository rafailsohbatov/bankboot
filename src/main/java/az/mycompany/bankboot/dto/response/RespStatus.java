package az.mycompany.bankboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespStatus {

    private Integer code;
    private String message;

    private static final Integer SUCCES_CODE = 1;
    private static final String SUCCESS_MESSAGE = "Success";

    public static RespStatus getSuccessRespStatus(){
        return new RespStatus(SUCCES_CODE,SUCCESS_MESSAGE);
    }
}
