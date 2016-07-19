package se.svennesson.svennelist.service;

import se.svennesson.svennelist.dao.TokenDAO;
import se.svennesson.svennelist.exception.AuthenticationException;
import se.svennesson.svennelist.exception.ForbiddenException;
import se.svennesson.svennelist.model.AccessToken;
import se.svennesson.svennelist.model.BasicCredentials;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.util.PBKDF2Hash;

import java.util.UUID;

public class TokenService {

    private final TokenDAO tokenDao;
    private final UserService userService;

    public TokenService(TokenDAO tokenDao, UserService userService) {
        this.tokenDao = tokenDao;
        this.userService = userService;
    }

    public AccessToken getTokenByToken(UUID token) {
        return tokenDao.findAccessToken(token)
                .orElseThrow(() -> new ForbiddenException("User is not authenticated"));
    }

    public AccessToken loginUser(BasicCredentials credentials) {
        final User user = userService.getUserByEmail(credentials.getEmail())
                .orElseThrow(() -> new AuthenticationException("Wrong email and/or password"));

        if (!PBKDF2Hash.validatePassword(credentials.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Wrong email and/or password");
        }

        if (tokenDao.checkIfAlreadyLoggedIn(user.getId())) {
            return tokenDao.generateToken(user);
        }


    }
}
