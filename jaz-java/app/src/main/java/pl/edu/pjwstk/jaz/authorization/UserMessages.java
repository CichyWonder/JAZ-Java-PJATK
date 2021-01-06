package pl.edu.pjwstk.jaz.authorization;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMessages {

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/helloAdmin")
    public String messageForAdmin(){
        return "Hello Admin";
    }

    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/helloUser")
    public String messageForUser(){
        return "Hello User";
    }
}
