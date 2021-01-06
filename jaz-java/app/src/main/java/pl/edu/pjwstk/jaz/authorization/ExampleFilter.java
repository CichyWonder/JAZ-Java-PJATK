package pl.edu.pjwstk.jaz.authorization;

import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class ExampleFilter extends HttpFilter {
    private final UserSession userSession;

    public ExampleFilter(UserSession userSession){
        this.userSession = userSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        if(isUserLogged() || isSiteAllowed(request)){
            chain.doFilter(request, response);
        }
        else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private boolean isSiteAllowed(HttpServletRequest request){
        return false;
        //return request.getContextPath().equals(....) || request.getContextPath().equals(....);

    }

    private boolean isUserLogged(){
        return userSession.isLoggedIn();
    }
}
