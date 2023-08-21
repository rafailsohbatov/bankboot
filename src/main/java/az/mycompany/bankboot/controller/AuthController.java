package az.mycompany.bankboot.controller;

import az.mycompany.bankboot.config.MyUserDetails;
import az.mycompany.bankboot.dto.request.ReqAuth;
import az.mycompany.bankboot.dto.response.RespAuth;
import az.mycompany.bankboot.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid ReqAuth request) {

        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            RespAuth respAuth = new RespAuth(user.getUsername(), jwtTokenUtil.generateAccessToken(user));
            return ResponseEntity.ok().body(respAuth);
        } catch (BadCredentialsException be){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
