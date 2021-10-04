package net.andymc12.restfuljava.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(value = "/person", 
                produces = {"application/json", "application/xml", "application/yaml"})
public class PersonController {

    Map<Integer, Person> personMap = new HashMap<>();
    AtomicInteger nextId = new AtomicInteger();

    public PersonController() {
        personMap.put(1, new Person("Bear", "Papa", 36, Color.RED));
        personMap.put(2, new Person("Bear", "Mama", 35, Color.VIOLET));
        personMap.put(3, new Person("Bear", "Baby", 8, Color.BLUE));
        personMap.put(4, new Person("Locks", "Goldy", 10, Color.YELLOW));
        nextId.set(5);
    }

    @GetMapping
    public @ResponseBody Set<Integer> allPersonIDs() {
        return personMap.keySet();
    }

    @GetMapping("/{id}")
    public @ResponseBody Person getPerson(@PathVariable int id) {
        return Optional.ofNullable(personMap.get(id)).orElseThrow(() ->
            error(HttpStatus.NOT_FOUND, "Unknown ID: %d", id));
    }

    @PostMapping(consumes = {"application/json", "application/xml", "application/yaml"})
    public @ResponseBody int createPerson(@RequestBody Person p) {
        int id = nextId.getAndIncrement();
        if (null != personMap.putIfAbsent(id, p)) {
            throw error(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate ID: %d", id);
        }
        return id;
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml", "application/yaml"})
    public @ResponseBody Person putPerson(@PathVariable int id, @RequestBody Person p) {
        if (!personMap.containsKey(id)) {
            throw error(HttpStatus.NOT_FOUND, "Unknown ID: %d", id);
        }
        return personMap.put(id, p);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Person deletePerson(@PathVariable int id) {
        return Optional.ofNullable(personMap.remove(id)).orElseThrow(() ->
            error(HttpStatus.NOT_FOUND, "Unknown ID: %d", id));
    }

    private ResponseStatusException error(HttpStatus status, String message, int id) {
        return new ResponseStatusException(status, String.format(message, id));
    }
}
