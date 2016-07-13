package se.svennesson.svennelist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotValidEntityException extends WebApplicationException{

    public NotValidEntityException() {
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }

    public NotValidEntityException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build());
    }
}
