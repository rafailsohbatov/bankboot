package az.mycompany.bankboot.controller;


import az.mycompany.bankboot.dto.request.ReqCustomer;
import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.dto.response.RespCustomer;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.entity.Customer;
import az.mycompany.bankboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/GetCustomerList",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public Response<List<RespCustomer>> getCustomerList(@RequestBody ReqToken reqToken) {
        return customerService.getCustomerList(reqToken);
    }

    @GetMapping("/GetCustomerById")
    public Response<RespCustomer> getCustomerById(@RequestParam("custId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/AddCustomer")
    public Response addCustomer (@RequestBody ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }
    @PutMapping("/UpdateCustomer")
    public Response udateCustomer (@RequestBody ReqCustomer reqCustomer){
        return customerService.updateCustomer(reqCustomer);
    }
    @PutMapping("/DeleteCustomer/{custId}")
    public Response deleteCustomer (@PathVariable("custId") Long customerId){
        return customerService.deleteCustomer(customerId);
    }
}
