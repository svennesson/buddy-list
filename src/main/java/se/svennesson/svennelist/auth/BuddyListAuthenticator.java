package se.svennesson.svennelist.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import se.svennesson.svennelist.exception.ForbiddenException;
import se.svennesson.svennelist.model.AccessToken;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.service.TokenService;
import se.svennesson.svennelist.service.UserService;

import java.util.UUID;

public class BuddyListAuthenticator implements Authenticator<String, User>{

    private final UserService userService;
    private final TokenService tokenService;

    public BuddyListAuthenticator(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        try {
            final UUID uuid = UUID.fromString(token);
            final AccessToken accessToken = tokenService.getTokenByToken(uuid);
            final User user = userService.getUserById(accessToken.getUserId()).orElse(null);

            return Optional.fromNullable(user);
        } catch (IllegalArgumentException e) {
            throw new ForbiddenException("User is not authenticated");
        }
    }
}
