package az.mycompany.bankboot.repository;

import az.mycompany.bankboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByActive(Integer active);
    Customer findByIdAndActive(Long customerId,Integer active);
}
