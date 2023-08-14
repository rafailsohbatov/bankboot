package az.mycompany.bankboot.service;

import az.mycompany.bankboot.dto.request.ReqCustomer;
import az.mycompany.bankboot.dto.response.RespCustomer;
import az.mycompany.bankboot.dto.response.Response;

import java.util.List;


public interface CustomerService {
    Response<List<RespCustomer>> getCustomerList();

    Response<RespCustomer> getCustomerById(Long customerId);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long customerId);
}
