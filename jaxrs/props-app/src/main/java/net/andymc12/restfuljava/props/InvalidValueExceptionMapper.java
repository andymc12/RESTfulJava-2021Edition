package net.andymc12.restfuljava.props;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import net.andymc12.propdb.InvalidValueException;

@Provider
public class InvalidValueExceptionMapper implements ExceptionMapper<InvalidValueException> {

    @Override
    public Response toResponse(InvalidValueException ex) {
        return Response.status(Status.BAD_REQUEST).entity("Invalid Value: " + ex.getMessage()).build();
    }
}