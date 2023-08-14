package az.mycompany.bankboot.service.impl;

import az.mycompany.bankboot.dto.request.ReqAccount;
import az.mycompany.bankboot.dto.response.RespAccount;
import az.mycompany.bankboot.dto.response.RespCustomer;
import az.mycompany.bankboot.dto.response.RespStatus;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.entity.Account;
import az.mycompany.bankboot.entity.Customer;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.exception.BankException;
import az.mycompany.bankboot.exception.ExceptionConstant;
import az.mycompany.bankboot.repository.AccountRepository;
import az.mycompany.bankboot.repository.CustomerRepository;
import az.mycompany.bankboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response<RespAccount> getAccountById(Long accountId) {
        Response<RespAccount> response = new Response<>();
        try {
            if (accountId == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Account account = accountRepository.findByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account Not Found");
            }
            RespAccount respAccount = convert(account);
            response.setT(respAccount);
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
    public Response<List<RespAccount>> getAccountByCustomerId(Long customerId) {
        Response<List<RespAccount>> response = new Response<>();
        List<RespAccount> respAccountList = new ArrayList<>();
        try {
            if (customerId == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer Not Found");
            }
            List<Account> accountList = accountRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.getValue());
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account Not Found");
            }
            for (Account account : accountList) {
                RespAccount respAccount = convert(account);
                respAccountList.add(respAccount);
            }
            response.setT(respAccountList);
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
    public Response<List<RespAccount>> getAccountList() {
        Response<List<RespAccount>> response = new Response<>();
        List<RespAccount> respAccountList = new ArrayList<>();
        try {
            List<Account> accountList = accountRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account Not Found");
            }
            for (Account account : accountList) {
                RespAccount respAccount = convert(account);
                respAccountList.add(respAccount);
            }
            response.setT(respAccountList);
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
    public Response addAccount(ReqAccount reqAccount) {
        Response response = new Response();
        try {
            if (reqAccount == null) {
               throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid Request Data");
            }
            String name = reqAccount.getName();
            Long customerId = reqAccount.getCustomerId();
            if(name == null || customerId == null){
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid Request Data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if(customer == null){
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND,"Customer Not Found");
            }
            Account account = new Account();
            account.setName(name);
            account.setAccountNo(reqAccount.getAccountNo());
            account.setIban(reqAccount.getIban());
            account.setCurrency(reqAccount.getCurrency());
            account.setCustomer(customer);
            accountRepository.save(account);
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

    private RespAccount convert(Account account) {
        RespAccount respAccount = new RespAccount();
        respAccount.setId(account.getId());
        respAccount.setName(account.getName());
        respAccount.setAccountNo(account.getAccountNo());
        respAccount.setIban(account.getIban());
        respAccount.setCurrency(account.getCurrency());
        if (account.getCustomer() == null) {
            respAccount.setRespStatus(new RespStatus(ExceptionConstant.CUSTOMER_NOT_FOUND_FOR_THIS_ACCOUNT
                    , "Customer Not Found For This Account"));
            return respAccount;
        }
        RespCustomer respCustomer = new RespCustomer();
        respCustomer.setId(account.getCustomer().getId());
        respCustomer.setName(account.getCustomer().getName());
        respCustomer.setSurname(account.getCustomer().getSurname());
        respAccount.setRespCustomer(respCustomer);
        return respAccount;
    }
}
