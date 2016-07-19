package se.svennesson.svennelist.resources;

import io.dropwizard.hibernate.UnitOfWork;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final UserService userService;

    public RegisterResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @UnitOfWork
    public Response createUser(@Valid User user) {
        final User persistedUser = userService.createUser(user);
        return Response.ok(persistedUser).build();
    }
}
