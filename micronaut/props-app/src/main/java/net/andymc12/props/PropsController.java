package net.andymc12.props;

import java.io.IOException;

import net.andymc12.propdb.DuplicateKeyException;
import net.andymc12.propdb.InvalidKeyException;
import net.andymc12.propdb.InvalidValueException;
import net.andymc12.propdb.PropsDB;
import net.andymc12.propdb.UnknownKeyException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CustomHttpMethod;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

@Controller(value = "/props", consumes = { "*/*" }, produces = { "text/plain", "text/html" })
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

    @Post("/{key}")
    public HttpResponse<String> post(@PathVariable String key, @Body String value) 
        throws DuplicateKeyException, InvalidKeyException, InvalidValueException, IOException {
            try {
                db.createProperty(key, value);
                return HttpResponse.noContent();
            } catch (DuplicateKeyException ex) {
               return HttpResponse.status(HttpStatus.CONFLICT).body("Duplicate key: " + ex.getMessage());
            }
    }

    @Get("/{key}")
    public String get(@PathVariable String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.readProperty(key);
    }

    @Put("/{key}")
    public String put(@PathVariable String key, @Body String value) 
        throws InvalidKeyException, InvalidValueException, UnknownKeyException, IOException {
        return db.updateProperty(key, value);
    }

    @Delete("/{key}")
    public String delete(@PathVariable String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.deleteProperty(key);
    }

    @CustomHttpMethod(method = "PREPEND", uri = "/{key}")
    public String prepend(@PathVariable String key, @Body String preText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, preText + db.readProperty(key));
        }
    }

    @CustomHttpMethod(method = "APPEND", uri = "/{key}")
    public String append(@PathVariable String key, @Body String postText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, db.readProperty(key) + postText);
        }
    }

    @Error(exception = UnknownKeyException.class)
    @SuppressWarnings("rawtypes")
    public HttpResponse<String> onUnknownKeyException(HttpRequest request, UnknownKeyException ex) {
        return HttpResponse.status(HttpStatus.NOT_FOUND).body("Unknown key: " + ex.getMessage());
    }
}
