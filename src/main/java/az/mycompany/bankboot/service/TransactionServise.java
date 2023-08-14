package az.mycompany.bankboot.service;

import az.mycompany.bankboot.dto.request.ReqTransaction;
import az.mycompany.bankboot.dto.response.RespTransaction;
import az.mycompany.bankboot.dto.response.Response;

import java.util.List;

public interface TransactionServise {
    Response<List<RespTransaction>> getTransactionList();

    Response addTransaction(ReqTransaction reqTransaction);
}
