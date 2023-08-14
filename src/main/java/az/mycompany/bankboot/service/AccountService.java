package az.mycompany.bankboot.service;

import az.mycompany.bankboot.dto.request.ReqAccount;
import az.mycompany.bankboot.dto.response.RespAccount;
import az.mycompany.bankboot.dto.response.Response;

import java.util.List;

public interface AccountService {
    Response<RespAccount> getAccountById(Long accountId);

    Response<List<RespAccount>> getAccountByCustomerId(Long customerId);

    Response<List<RespAccount>> getAccountList();

    Response addAccount(ReqAccount reqAccount);
}
