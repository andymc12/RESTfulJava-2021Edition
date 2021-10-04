package net.andymc12.restfuljava.props;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import net.andymc12.propdb.InvalidKeyException;

@Provider
public class InvalidKeyExceptionMapper implements ExceptionMapper<InvalidKeyException> {

    @Override
    public Response toResponse(InvalidKeyException ex) {
        return Response.status(Status.BAD_REQUEST).entity("Invalid Key: " + ex.getMessage()).build();
    }
}