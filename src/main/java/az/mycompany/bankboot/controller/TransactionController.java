package az.mycompany.bankboot.controller;


import az.mycompany.bankboot.dto.request.ReqTransaction;
import az.mycompany.bankboot.dto.response.RespTransaction;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.service.TransactionServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/AddTransaction")
    public Response addTransaction(@RequestBody ReqTransaction reqTransaction){
        return transactionServise.addTransaction(reqTransaction);
    }

    @PutMapping("/UpdateTransaction")
    public Response updateTransaction(@RequestBody ReqTransaction reqTransaction){
        return transactionServise.updateTransaction(reqTransaction);
    }

}
