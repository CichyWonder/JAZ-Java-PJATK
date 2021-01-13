package pl.edu.pjwstk.jaz.authorizationjpa;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.authorization.AuthenticationService;
import pl.edu.pjwstk.jaz.authorization.LoginRequest;
import pl.edu.pjwstk.jaz.authorization.UnauthorizedExecption;
import pl.edu.pjwstk.jaz.authorization.UserSession;

@RestController
public class LoginControllerJpa {
    private final AuthenticationService authenticationService;
    private final UserSession userSession;

    public LoginControllerJpa (AuthenticationService authenticationService,UserSession userSession ){
        this.authenticationService=authenticationService;
        this.userSession = userSession;
    }
    @PostMapping("/loginjpa")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.loginjpa(loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
            throw new UnauthorizedExecption();
        }
        userSession.setIslogged();
    }
}
