package az.mycompany.bankboot.util;

import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.entity.User;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.exception.BankException;
import az.mycompany.bankboot.exception.ExceptionConstant;
import az.mycompany.bankboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UserRepository userRepository;

    public User checkToken(ReqToken reqToken){
        String token = reqToken.getToken();
        Long userId = reqToken.getUserId();
        if(userId == null || token == null){
            throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid Request Data");
        }
        User user = userRepository.findUserByIdAndTokenAndActive(userId,token, EnumAvailableStatus.ACTIVE.getValue());
        if(user == null){
            throw new BankException(ExceptionConstant.INVALID_TOKEN,"Invalid Token");
        }
        return user;
    }
}
