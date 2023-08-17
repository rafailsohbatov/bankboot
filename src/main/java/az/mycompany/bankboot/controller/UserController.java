package az.mycompany.bankboot.controller;


import az.mycompany.bankboot.dto.request.ReqToken;
import az.mycompany.bankboot.dto.request.ReqUser;
import az.mycompany.bankboot.dto.response.RespUser;
import az.mycompany.bankboot.dto.response.Response;
import az.mycompany.bankboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/Login")
    public RespUser login(@RequestBody ReqUser reqUser){
        return userService.login(reqUser);
    }

    @PostMapping("/Logout")
    public Response logout(@RequestBody ReqToken reqToken){
        return userService.logout(reqToken);
    }
}
