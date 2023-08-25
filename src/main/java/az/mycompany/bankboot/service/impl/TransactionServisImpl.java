package az.mycompany.bankboot.service.impl;

import az.mycompany.bankboot.dto.request.ReqTransaction;
import az.mycompany.bankboot.dto.response.*;
import az.mycompany.bankboot.entity.Account;
import az.mycompany.bankboot.entity.Customer;
import az.mycompany.bankboot.entity.Transaction;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.exception.BankException;
import az.mycompany.bankboot.exception.ExceptionConstant;
import az.mycompany.bankboot.repository.AccountRepository;
import az.mycompany.bankboot.repository.TransactionRepository;
import az.mycompany.bankboot.service.TransactionServise;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServisImpl implements TransactionServise {
    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionServisImpl.class);
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Response<List<RespTransaction>> getTransactionList() {
        Response<List<RespTransaction>> response = new Response<>();
        List<RespTransaction> respTransactionList = new ArrayList<>();
        try {
            List<Transaction> transactionList = transactionRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (transactionList.isEmpty()) {
                throw new BankException(ExceptionConstant.TRANSACTION_NOT_FOUND, "Transaction Not Found");
            }
            for (Transaction transaction : transactionList) {
                RespTransaction respTransaction = convert(transaction);
                respTransactionList.add(respTransaction);
            }
            response.setT(respTransactionList);
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
    public Response addTransaction(ReqTransaction reqTransaction) {
        Response response = new Response();
        try {
            LOGGER.info("Add Transaction request: "+ reqTransaction);
            Long accountId = reqTransaction.getAccountId();
            String crAccount = reqTransaction.getCrAccount();
            if (reqTransaction == null || accountId == null || crAccount == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Account account = accountRepository.findByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account Not Found");
            }
            Transaction transaction = new Transaction();
            transaction.setDtAccount(account);
            transaction.setCrAccount(crAccount);
            Double amount = reqTransaction.getAmount() * 100;
            transaction.setAmount(amount.intValue());
            transaction.setCurrency(reqTransaction.getCurrency());
            transactionRepository.save(transaction);
            response.setRespStatus(RespStatus.getSuccessRespStatus());
            LOGGER.info("Add Transaction response: is Success");
        } catch (BankException be) {
            response.setRespStatus(new RespStatus(be.getCode(), be.getMessage()));
            be.printStackTrace();
            LOGGER.info("Add Transaction response: " + be.getMessage());
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
            LOGGER.info("Add Transaction response: " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response updateTransaction(ReqTransaction reqTransaction) {
        Response response = new Response();
        try {
            Long transactionId = reqTransaction.getId();
            Long accountId = reqTransaction.getAccountId();
            String crAccount = reqTransaction.getCrAccount();
            if (reqTransaction == null || transactionId == null || accountId == null || crAccount == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Transaction transaction = transactionRepository.findByIdAndActive(transactionId, EnumAvailableStatus.ACTIVE.getValue());
            if (transaction == null) {
                throw new BankException(ExceptionConstant.TRANSACTION_NOT_FOUND, "Transaction Not Found");
            }
            Account account = accountRepository.findByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account Not Found");
            }
            transaction.setDtAccount(account);
            transaction.setCrAccount(crAccount);
            Double amount = reqTransaction.getAmount() * 100;
            transaction.setAmount(amount.intValue());
            transaction.setCurrency(reqTransaction.getCurrency());
            transactionRepository.save(transaction);
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

    private RespTransaction convert(Transaction transaction) {
        RespTransaction respTransaction = RespTransaction.builder()
                .crAccount(transaction.getCrAccount())
                .currency(transaction.getCurrency())
                .amount((double) transaction.getAmount() / 100)
                .id(transaction.getId())
                .build();
        Account account = transaction.getDtAccount();
        if (account == null) {
            respTransaction.setRespStatus(new RespStatus(ExceptionConstant.ACCOUNT_NOT_FOUND_FOR_THIS_TRANSACTION
                    , "Account Not Found For This Transaction"));
            return respTransaction;
        }
        RespAccount respAccount = RespAccount.builder().name(transaction.getDtAccount().getName())
                .accountNo(transaction.getDtAccount().getAccountNo())
                .iban(transaction.getDtAccount().getIban())
                .currency(transaction.getDtAccount().getCurrency())
                .build();
        respTransaction.setDtAccount(respAccount);
        Customer customer = account.getCustomer();
        if (customer == null) {
            respAccount.setRespStatus(new RespStatus(ExceptionConstant.CUSTOMER_NOT_FOUND_FOR_THIS_ACCOUNT
                    , "Custoemer Not Found For This Transaction"));
            return respTransaction;
        }
        RespCustomer respCustomer = RespCustomer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .dob(df.format(customer.getDob()))
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .cif(customer.getCif())
                .fin(customer.getFin())
                .seria(customer.getSeria())
                .build();
        respAccount.setRespCustomer(respCustomer);
        return respTransaction;
    }

}

