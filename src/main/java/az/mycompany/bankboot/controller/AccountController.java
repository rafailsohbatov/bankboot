package az.mycompany.bankboot.controller;


import az.mycompany.bankboot.dto.request.ReqAccount;
import az.mycompany.bankboot.dto.request.ReqCustomer;
import az.mycompany.bankboot.dto.response.RespAccount;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/GetAccountList")
    public Response<List<RespAccount>> getAccountList(){
        return accountService.getAccountList();
    }
    @GetMapping("/GetAccountByCustomerId/{custId}")
    public Response<List<RespAccount>> getAccountByCustomerId(@PathVariable("custId") Long customerId){
        return accountService.getAccountByCustomerId(customerId);
    }
    @GetMapping("/GetAccountById/{accId}")
    public Response<RespAccount> getAccountById(@PathVariable("accId")Long accountId){
        return accountService.getAccountById(accountId);
    }
    @PostMapping("/AddAccount")
    public Response addAccount(@RequestBody ReqAccount reqAccount){
        return accountService.addAccount(reqAccount);
    }
}
