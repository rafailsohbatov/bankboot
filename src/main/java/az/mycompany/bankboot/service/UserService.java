package az.mycompany.bankboot.service;

import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.dto.request.ReqUser;
import az.mycompany.bankboot.dto.response.RespUser;
import az.mycompany.bankboot.dto.response.Response;

public interface UserService {
    RespUser login(ReqUser reqUser);

    Response logout(ReqToken reqToken);
}
