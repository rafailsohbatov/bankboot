package az.mycompany.bankboot.repository;


import az.mycompany.bankboot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findUsersByEmailAndActive(String email,Integer active);
}
