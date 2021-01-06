package pl.edu.pjwstk.jaz.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.IM_USED)
public class UserAlreadyExistsExecptions extends RuntimeException {
}
