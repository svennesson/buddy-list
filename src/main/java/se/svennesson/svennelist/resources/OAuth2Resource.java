package se.svennesson.svennelist.resources;

import io.dropwizard.hibernate.UnitOfWork;
import se.svennesson.svennelist.model.BasicCredentials;
import se.svennesson.svennelist.service.TokenService;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("oauth2")
public class OAuth2Resource {

    private final TokenService tokenService;

    public OAuth2Resource(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @POST
    @UnitOfWork
    @Path("token")
    public Response requestAccessToken(@Valid BasicCredentials credentials) {
        return Response.ok(tokenService.loginUser(credentials)).build();
    }
}
