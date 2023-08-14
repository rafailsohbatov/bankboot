package az.mycompany.bankboot.controller;


import az.mycompany.bankboot.dto.response.RespTransaction;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.service.TransactionServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionServise transactionServise;

    @GetMapping("/GetTransactionList")
    public Response<List<RespTransaction>> getTransactionList(){
        return transactionServise.getTransactionList();
    }
}
