package net.andymc12.restfuljava.hello;

import java.util.concurrent.atomic.AtomicLong;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/greeting")
@Produces("application/json")
public class HelloController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();

    @GET
    public Greeting hello(@QueryParam("name") @DefaultValue("World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
