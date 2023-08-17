package az.mycompany.bankboot.service;

import az.mycompany.bankboot.dto.request.ReqCustomer;
import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.dto.response.RespCustomer;
import az.mycompany.bankboot.dto.response.Response;

import java.util.List;


public interface CustomerService {
    Response<List<RespCustomer>> getCustomerList(ReqToken reqToken);

    Response<RespCustomer> getCustomerById(Long customerId);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long customerId);
}
