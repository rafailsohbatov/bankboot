package az.mycompany.bankboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(name = "user_role")
    private String role;
    @ColumnDefault(value = "1")
    private Integer active;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
