package az.mycompany.bankboot.repository;

import az.mycompany.bankboot.entity.Account;
import az.mycompany.bankboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByIdAndActive(Long accountId, Integer active);

    List<Account> findAllByActive(Integer active);

    List<Account> findAllByCustomerAndActive(Customer customer, Integer active);
}
