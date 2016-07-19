package se.svennesson.svennelist.resources;

import io.dropwizard.hibernate.UnitOfWork;
import se.svennesson.svennelist.exception.EntityNotFoundException;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @UnitOfWork
    public Response getAllUsers() {
        return Response.ok(userService.getAllUsers()).build();
    }

    @GET
    @UnitOfWork
    @Path("{userId}")
    public Response getUser(@PathParam("userId") final Long id) {
        final User user = userService.getUserById(id)
                .orElseThrow(EntityNotFoundException::new);
        return Response.ok(user).build();
    }
}
