package az.mycompany.bankboot.repository;


import az.mycompany.bankboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsernameAndPasswordAndActive(String username,String password,Integer active);

    User findUserByIdAndTokenAndActive(Long userId,String token,Integer active);
}
