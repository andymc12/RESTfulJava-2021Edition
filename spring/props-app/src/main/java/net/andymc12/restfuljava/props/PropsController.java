// Example code from Spring Guide at https://spring.io/guides/gs/rest-service/
package net.andymc12.restfuljava.props;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import net.andymc12.propdb.DuplicateKeyException;
import net.andymc12.propdb.InvalidKeyException;
import net.andymc12.propdb.InvalidValueException;
import net.andymc12.propdb.PropsDB;
import net.andymc12.propdb.UnknownKeyException;

@RestController
@RequestMapping("/props/{key}")
public class PropsController {

	private final PropsDB db;

    public PropsController() {
        try {
            db = PropsDB.getDB("/tmp/demo.properties");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @PostMapping
    public void post(@PathVariable String key, @RequestBody String value)
            throws InvalidKeyException, InvalidValueException, IOException {
        try { 
            db.createProperty(key, value);
        } catch (DuplicateKeyException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate key: " + ex.getMessage());
        }
    }

    @GetMapping
    public String get(@PathVariable String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.readProperty(key);
    }

    @PutMapping
    public String put(@PathVariable String key, @RequestBody String value)
            throws InvalidKeyException, InvalidValueException, UnknownKeyException, IOException {
        return db.updateProperty(key, value);
    }

    @DeleteMapping
    public String delete(@PathVariable String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.deleteProperty(key);
    }

    // Custom HTTP methods requires a custom DispatcherServlet implementation and RequestMappingHandlingAdapter
    // see https://stackoverflow.com/questions/33302397/custom-http-methods-in-spring-mvc for details
    public String prepend(@PathVariable String key, @RequestBody String preText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, preText + db.readProperty(key));
        }
    }

    public String append(@PathVariable String key, @RequestBody String postText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, db.readProperty(key) + postText);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpEntity<String> handleUnknownKeyException(UnknownKeyException ex) {
        return new HttpEntity<String>("Unknown key: " + ex.getMessage());
    }
}
