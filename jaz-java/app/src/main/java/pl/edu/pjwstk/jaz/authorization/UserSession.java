package pl.edu.pjwstk.jaz.authorization;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class UserSession {

    private boolean islogged = false;


    public boolean isLoggedIn() {

        return islogged;
    }

    public void setIslogged() {
        islogged = true;
    }

    public void logout() {
        islogged = false;
    }

}
