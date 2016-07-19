package se.svennesson.svennelist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ForbiddenException extends WebApplicationException{

    public ForbiddenException() {
        super(Response.status(Response.Status.FORBIDDEN).build());
    }

    public ForbiddenException(String message) {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity(message)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build());
    }
}
