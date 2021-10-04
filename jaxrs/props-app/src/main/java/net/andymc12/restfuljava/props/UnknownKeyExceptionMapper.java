package net.andymc12.restfuljava.props;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import net.andymc12.propdb.UnknownKeyException;

@Provider
public class UnknownKeyExceptionMapper implements ExceptionMapper<UnknownKeyException> {

    @Override
    public Response toResponse(UnknownKeyException ex) {
        return Response.status(Status.NOT_FOUND).entity("Unknown Key: " + ex.getMessage()).build();
    }
}