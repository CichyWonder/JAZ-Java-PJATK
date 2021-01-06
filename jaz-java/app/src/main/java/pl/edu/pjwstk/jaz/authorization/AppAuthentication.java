package pl.edu.pjwstk.jaz.authorization;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AppAuthentication extends AbstractAuthenticationToken {

    private User authenticatedUser;


public AppAuthentication(User authenticatedUser) {
    super(toGrantedAuthorieties(authenticatedUser.getAuthorities()));
    this.authenticatedUser = authenticatedUser;
    setAuthenticated(true);
}

private static Collection<? extends GrantedAuthority> toGrantedAuthorieties(Set<String> authorieties){

    return authorieties.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

}

@Override
public Object getCredentials(){
    return authenticatedUser.getPassword();
}

    @Override
    public Object getPrincipal(){
        return authenticatedUser;
    }
}
