package net.andymc12.props;

import jakarta.inject.Singleton;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import net.andymc12.propdb.InvalidKeyException;

@Singleton
@Produces
@Requires(classes = {InvalidKeyException.class, ExceptionHandler.class})
public class InvalidKeyExceptionHandler implements ExceptionHandler<InvalidKeyException, HttpResponse<String>> {

    @Override
    @SuppressWarnings("rawtypes")
    public HttpResponse<String> handle(HttpRequest request, InvalidKeyException ex) {
        return HttpResponse.badRequest("Invalid key: " + ex.getMessage());
    }
}