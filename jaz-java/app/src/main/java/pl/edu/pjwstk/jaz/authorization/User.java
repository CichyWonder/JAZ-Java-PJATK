package pl.edu.pjwstk.jaz.authorization;

import java.util.Set;

public class User {

    private Set<String> authorities;

    private String username;

    private String password;

    public User(String username, String password, Set <String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}