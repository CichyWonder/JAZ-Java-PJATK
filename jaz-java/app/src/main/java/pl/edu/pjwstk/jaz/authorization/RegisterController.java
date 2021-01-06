package pl.edu.pjwstk.jaz.authorization;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;


@RestController
public class RegisterController {

    UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Map register(@RequestBody RegisterRequest registerRequest){


        User user = new User(registerRequest.getUsername(),registerRequest.getPassword());
        user.setAuthorities(Set.of("user"));

        if (userRepository.getUserMap().isEmpty()) {
            user.setAuthorities(Set.of("admin"));
        }

        userRepository.add(user);

        return userRepository.getUserMap();
    }

    @GetMapping("/map")
    public Map<String, User> ReturnUserMap(){
        return userRepository.getUserMap();
    }
}


