package se.svennesson.svennelist.auth;

import io.dropwizard.auth.Authorizer;
import se.svennesson.svennelist.model.User;

public class BuddyListAuthorizer implements Authorizer<User>{

    @Override
    public boolean authorize(User user, String role) {
        return user.getRole().name().equalsIgnoreCase(role);
    }
}
