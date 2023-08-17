package az.mycompany.bankboot.service.impl;


import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.dto.request.ReqUser;
import az.mycompany.bankboot.dto.response.RespStatus;
import az.mycompany.bankboot.dto.response.RespToken;
import az.mycompany.bankboot.dto.response.RespUser;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.entity.User;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.exception.BankException;
import az.mycompany.bankboot.exception.ExceptionConstant;
import az.mycompany.bankboot.repository.UserRepository;
import az.mycompany.bankboot.service.UserService;
import az.mycompany.bankboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Utility utility;

    @Override
    public RespUser login(ReqUser reqUser) {
        RespUser response = new RespUser();
        try {
            String username = reqUser.getUsername();
            String password = reqUser.getPassword();
            if (username == null || password == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            User user = userRepository
                    .findUserByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.getValue());
            if (user == null) {
                throw new BankException(ExceptionConstant.INVALID_USER, "Invalid User");
            }
            if (user.getToken() != null) {
                throw new BankException(ExceptionConstant.USER_ALREADY_EXIST_IN_SESSION, "User Already Exist In Session");
            }
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userRepository.save(user);
            response.setFullName(user.getFullName());
            response.setUsername(user.getUsername());
            RespToken respToken = new RespToken();
            respToken.setToken(token);
            respToken.setUserId(user.getId());
            response.setRespToken(respToken);
            response.setRespStatus(RespStatus.getSuccessRespStatus());
        } catch (BankException be) {
            response.setRespStatus(new RespStatus(be.getCode(), be.getMessage()));
            be.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response logout(ReqToken reqToken) {
        Response response = new Response();
        try {
            User user = utility.checkToken(reqToken);
            user.setToken(null);
            userRepository.save(user);
            response.setRespStatus(RespStatus.getSuccessRespStatus());
        } catch (BankException be) {
            response.setRespStatus(new RespStatus(be.getCode(), be.getMessage()));
            be.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }
}
