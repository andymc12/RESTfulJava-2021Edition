package net.andymc12.restfuljava;

import java.util.concurrent.atomic.AtomicLong;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller(value = "/greeting", consumes = { "*/*" }, produces = { "application/json" })
public class HelloController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Get
    public Greeting get(@QueryValue(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
