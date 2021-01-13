package pl.edu.pjwstk.jaz.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;
import pl.edu.pjwstk.jaz.authorizationjpa.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthenticationService {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public boolean login(String username, String password) {

        boolean login = userRepository.getUserMap().containsKey(username) &&
                userRepository.getUserMap().get(username).getPassword().equals(password);


        if (login) {
            var user = userRepository.getUserMap().get(username);
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
        }
        return login;
    }

    public boolean loginjpa(String username, String password) {
        boolean login = userService.doesUserExist(username);

        if (login) {
            final UserEntity currentUser = userService.findUserByUsername(username);
            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, currentUser.getPassword())) {
                String[] authoritiesArray = currentUser.getRole().split(",");
                Set<String> authoritiesAsSet = new HashSet<String>(Arrays.asList(authoritiesArray));
                User user = new User(currentUser.getUsername(), currentUser.getPassword(), authoritiesAsSet);
                SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
                return login;
            } else {
                login = false;
                return login;
            }
        } else {
            return login;
        }

    }
}
