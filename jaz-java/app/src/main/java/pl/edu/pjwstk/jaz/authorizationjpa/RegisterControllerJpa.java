package pl.edu.pjwstk.jaz.authorizationjpa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.authorization.RegisterRequest;
import pl.edu.pjwstk.jaz.authorization.User;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RegisterControllerJpa {
    UserService userService;

    public RegisterControllerJpa (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerjpa")
    @Transactional
    public void register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response){

        if(userService.doesUserExist (registerRequest.getUsername ())){
            response.setStatus (HttpStatus.CONFLICT.value ());

        }else {
            if(registerRequest.getPassword ()==null || registerRequest.getPassword().equals("")){
                response.setStatus (HttpStatus.CONFLICT.value ());
            }else {
                Set<String> authorities = new HashSet<>();
                if(userService.isEmpty ()){
                    authorities.add ("admin");
                }
                authorities.add ("user");
                userService.saveUser (new User(registerRequest.getUsername (),registerRequest.getPassword (),authorities)); //New user created.
            }
        }
    }
}