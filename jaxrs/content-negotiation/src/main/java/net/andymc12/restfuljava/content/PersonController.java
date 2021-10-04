package net.andymc12.restfuljava.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("person")
@Consumes({"application/json", "application/xml", "application/yaml"})
@Produces({"application/json", "application/xml", "application/yaml"})
@ApplicationScoped
public class PersonController {

    private Map<Integer, Person> personMap = new HashMap<>();
    private AtomicInteger nextId = new AtomicInteger();

    @PostConstruct
    public void initMap() {
        personMap.put(1, new Person("Bear", "Papa", 36, Color.RED));
        personMap.put(2, new Person("Bear", "Mama", 35, Color.VIOLET));
        personMap.put(3, new Person("Bear", "Baby", 8, Color.BLUE));
        personMap.put(4, new Person("Locks", "Goldy", 10, Color.YELLOW));
        nextId.set(5);
    }

    @GET
    public Set<Integer> allPersonIDs() {
        return personMap.keySet();
    }

    @GET
    @Path("/{id}")
    public Person getPerson(@PathParam("id") int id) {
        return Optional.ofNullable(personMap.get(id)).orElseThrow(() ->
            error(404, "Unknown ID: %d", id));
    }

    @POST
    public int createPerson(Person p) {
        int id = nextId.getAndIncrement();
        if (null != personMap.putIfAbsent(id, p)) {
            throw error(500, "Duplicate ID: %d", id);
        }
        return id;
    }

    @PUT
    @Path("/{id}")
    public Person putPerson(@PathParam("id") int id, Person p) {
        if (!personMap.containsKey(id)) {
            throw error(404, "Unknown ID: %d", id);
        }
        return personMap.put(id, p);
    }

    @DELETE
    @Path("/{id}")
    public Person deletePerson(@PathParam("id") int id) {
        return Optional.ofNullable(personMap.remove(id)).orElseThrow(() ->
            error(404, "Unknown ID: %d", id));
    }

    private WebApplicationException error(int status, String message, int id) {
        Response r = Response.status(status).entity(new ErrorObject(message, id)).build();
        return new WebApplicationException(r);
    }
}
