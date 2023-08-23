package az.mycompany.bankboot.jwt;

import az.mycompany.bankboot.config.MyUserDetails;
import az.mycompany.bankboot.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);

        if(!jwtTokenUtil.validateAccessToken(token)){
            filterChain.doFilter(request,response);
            return;
        }
        setAuthenticationContext(token,request);
        filterChain.doFilter(request,response);
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        Users users = new Users();
        System.out.println(jwtTokenUtil.getSubject(token));

        String[] subjectArr = jwtTokenUtil.getSubject(token).split(",");

        users.setEmail(subjectArr[0]);
        System.out.println(subjectArr[1].substring(1,subjectArr[1].length()-1));
        if(subjectArr[1] != null)
            users.setRole(subjectArr[1].replace("[","").replace("]",""));
        return new MyUserDetails(users);
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }
}
