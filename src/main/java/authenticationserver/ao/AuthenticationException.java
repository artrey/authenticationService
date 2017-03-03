package authenticationserver.ao;

/**
 * Created by igor on 26.02.17.
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(Throwable t) {
        super(t);
    }
}
