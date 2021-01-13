package pl.edu.pjwstk.jaz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.pjwstk.jaz.authorization.UserRepository;
import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;
import pl.edu.pjwstk.jaz.authorizationjpa.UserRepositoryJpa;


@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

