package az.mycompany.bankboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BankbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankbootApplication.class, args);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("rafail123"));
        System.out.println(passwordEncoder.encode("zahid123"));
    }

}
