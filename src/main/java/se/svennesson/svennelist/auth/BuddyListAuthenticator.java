package se.svennesson.svennelist.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.service.TokenService;
import se.svennesson.svennelist.service.UserService;

public class BuddyListAuthenticator implements Authenticator<String, User>{

    //private final UserService userService;
    //private final TokenService tokenService;

    @Override
    public Optional<User> authenticate(String s) throws AuthenticationException {
        return null;
    }
}
