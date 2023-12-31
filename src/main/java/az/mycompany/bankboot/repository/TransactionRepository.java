package az.mycompany.bankboot.repository;


import az.mycompany.bankboot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByActive(Integer active);
    Transaction findByIdAndActive(Long id,Integer active);
}
