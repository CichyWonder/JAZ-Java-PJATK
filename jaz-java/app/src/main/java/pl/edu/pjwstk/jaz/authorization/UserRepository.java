package pl.edu.pjwstk.jaz.authorization;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private Map<String, User> userMap= new HashMap<>();
    public void add(User user){

        if(!userMap.containsKey(user.getUsername())){
            userMap.put(user.getUsername(), user);
        }
        else{
            throw new UserAlreadyExistsExecptions();
        }

    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Optional<User> findByUsername(String username){

        return Optional.ofNullable(userMap.get(username));
    }


}
