package net.andymc12.props;

import jakarta.inject.Singleton;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import net.andymc12.propdb.UnknownKeyException;

@Singleton
@Produces
@Requires(classes = {UnknownKeyException.class, ExceptionHandler.class})
public class UnknownKeyExceptionHandler implements ExceptionHandler<UnknownKeyException, HttpResponse<String>> {

    @Override
    @SuppressWarnings("rawtypes")
    public HttpResponse<String> handle(HttpRequest request, UnknownKeyException ex) {
        return HttpResponse.badRequest("Unknown key: " + ex.getMessage());
    }
}