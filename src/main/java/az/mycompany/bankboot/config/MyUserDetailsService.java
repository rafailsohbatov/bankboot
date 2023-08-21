package az.mycompany.bankboot.config;

import az.mycompany.bankboot.entity.Users;
import az.mycompany.bankboot.enums.EnumAvailableStatus;
import az.mycompany.bankboot.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findUsersByEmailAndActive(username, EnumAvailableStatus.ACTIVE.getValue());
        if(users == null){
            throw new UsernameNotFoundException("Not Found: "+username);
        }
        return new MyUserDetails(users);
    }
}
