package pl.edu.pjwstk.jaz.authorization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationService authenticationService;

    private final UserSession userSession;


    public LoginController(AuthenticationService authenticationService, UserSession userSession){
        this.authenticationService = authenticationService;
        this.userSession = userSession;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());


        if (!isLogged) {
            throw new UnauthorizedExecption();
        }

        userSession.setIslogged();
    }
    /*@GetMapping("/logout")
    public void logout(){
            if(userSession.isLoggedIn() == true){
                userSession.logout();
            }
    }*/
}
