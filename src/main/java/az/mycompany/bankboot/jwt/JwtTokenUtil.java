package az.mycompany.bankboot.jwt;


import az.mycompany.bankboot.config.MyUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 100; //24 HOUR
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(MyUserDetails user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s",user.getUsername(),user.getAuthorities()))
                .setIssuer("Rafail")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("Jwt Expired");
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace");
            ex.printStackTrace();
        }catch (MalformedJwtException ex) {
            System.out.println("Invalid Jwt Token");
            ex.printStackTrace();
        }catch (UnsupportedJwtException ex){
            System.out.println("Jwt Is Not Supported");
            ex.printStackTrace();
        }
        catch (SignatureException ex){
            System.out.println("Signature validation exp");
            ex.printStackTrace();
        }
        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
