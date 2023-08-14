package az.mycompany.bankboot.service.impl;

import az.mycompany.bankboot.dto.request.ReqCustomer;
import az.mycompany.bankboot.dto.response.RespCustomer;
import az.mycompany.bankboot.dto.response.RespStatus;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.entity.Customer;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.exception.BankException;
import az.mycompany.bankboot.exception.ExceptionConstant;
import az.mycompany.bankboot.repository.CustomerRepository;
import az.mycompany.bankboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Response<List<RespCustomer>> getCustomerList() {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (customerList.isEmpty()) {
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer Not Found");
            }
            List<RespCustomer> respCustomerList = new LinkedList<>();
            for (Customer customer : customerList) {
                RespCustomer respCustomer = convert(customer);
                respCustomerList.add(respCustomer);
            }
            response.setT(respCustomerList);
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
    public Response<RespCustomer> getCustomerById(Long customerId) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (customerId == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
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
    public Response addCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            if (reqCustomer == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || name.isEmpty() || surname == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Name or Surname Is Not Null");
            }
            Customer customer = new Customer();
            customer.setName(name);
            customer.setSurname(surname);
            customer.setDob(reqCustomer.getDob());
            customer.setAddress(reqCustomer.getAddress());
            customer.setPhone(reqCustomer.getPhone());
            customer.setCif(reqCustomer.getCif());
            customer.setFin(reqCustomer.getFin());
            customer.setSeria(reqCustomer.getSeria());
            customer.setEmail(reqCustomer.getEmail());
            customer.setUsername(reqCustomer.getUsername());
            customer.setPassword(reqCustomer.getPassword());

            customerRepository.save(customer);
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
    public Response updateCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (reqCustomer == null || reqCustomer.getId() == null ||
                    name == null || name.isEmpty() || surname == null || surname.isEmpty()) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Customer customer = customerRepository.findByIdAndActive(reqCustomer.getId(), EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer Not Found");
            }
            customer.setName(name);
            customer.setSurname(surname);
            customer.setDob(reqCustomer.getDob());
            customer.setAddress(reqCustomer.getAddress());
            customer.setPhone(reqCustomer.getPhone());
            customer.setCif(reqCustomer.getCif());
            customer.setFin(reqCustomer.getFin());
            customer.setSeria(reqCustomer.getSeria());
            customer.setEmail(reqCustomer.getEmail());
            customer.setUsername(reqCustomer.getUsername());
            customer.setPassword(reqCustomer.getPassword());
            customerRepository.save(customer);
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
    public Response deleteCustomer(Long customerId) {
        Response response = new Response();
        try {
            if (customerId == null) {
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer Not Found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            customerRepository.save(customer);
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


    private RespCustomer convert(Customer customer) {
        RespCustomer respCustomer = new RespCustomer();
        if (customer == null) {
            throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer Not Found");
        }
        respCustomer.setId(customer.getId());
        respCustomer.setUsername(customer.getUsername());
        respCustomer.setPassword(customer.getPassword());
        respCustomer.setName(customer.getName());
        respCustomer.setSurname(customer.getSurname());
        if(customer.getDob() != null){
            respCustomer.setDob(df.format(customer.getDob()));
        }
        respCustomer.setAddress(customer.getAddress());
        respCustomer.setPhone(customer.getPhone());
        respCustomer.setCif(customer.getCif());
        respCustomer.setFin(customer.getFin());
        respCustomer.setSeria(customer.getSeria());
        return respCustomer;
    }
}
