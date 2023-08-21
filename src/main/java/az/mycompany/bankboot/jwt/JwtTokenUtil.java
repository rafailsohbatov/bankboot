package az.mycompany.bankboot.jwt;


import az.mycompany.bankboot.config.MyUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 100; //24 HOUR
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(MyUserDetails user){
        return Jwts.builder()
                .setSubject(String.format(user.getUsername()))
                .setIssuer("Rafail")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
}
