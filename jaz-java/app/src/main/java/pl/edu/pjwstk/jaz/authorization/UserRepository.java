package pl.edu.pjwstk.jaz.authorization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
public class UserRepository {



    private Map<String, User> userMap = new HashMap<>();


    public void add(User user) {

        if (!userMap.containsKey(user.getUsername())) {
            userMap.put(user.getUsername(), user);
        } else {
            throw new UserAlreadyExistsExecptions();
        }

    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Optional<User> findByUsername(String username) {

        return Optional.ofNullable(userMap.get(username));
    }
}


