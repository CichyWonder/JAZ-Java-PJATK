package pl.edu.pjwstk.jaz.authorization;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String userName, String password){

        boolean login = userRepository.getUserMap().containsKey(userName) &&
                userRepository.getUserMap().get(userName).getPassword().equals(password);


        if (login){
            var user = userRepository.getUserMap().get(userName);
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
        }
        return login;
    }


}
