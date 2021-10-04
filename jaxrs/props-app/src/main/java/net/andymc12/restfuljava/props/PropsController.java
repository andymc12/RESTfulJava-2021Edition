package net.andymc12.restfuljava.props;

import java.io.IOException;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import net.andymc12.propdb.DuplicateKeyException;
import net.andymc12.propdb.InvalidKeyException;
import net.andymc12.propdb.InvalidValueException;
import net.andymc12.propdb.PropsDB;
import net.andymc12.propdb.UnknownKeyException;

@Path("/props/{key}")
@Produces({"text/plain", "text/html"})
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

    @POST
    public void post(@PathParam("key") String key, String value) 
        throws InvalidKeyException, InvalidValueException, IOException {
        try {
            db.createProperty(key, value);
        } catch (DuplicateKeyException ex) {
            throw new WebApplicationException(Response.status(409).entity("Duplicate key: " + ex.getMessage()).build());
        }
    }

    @GET
    public String get(@PathParam("key") String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.readProperty(key);
    }

    @PUT
    public String put(@PathParam("key") String key, String value)
            throws InvalidKeyException, InvalidValueException, UnknownKeyException, IOException {
        return db.updateProperty(key, value);
    }

    @DELETE
    public String delete(@PathParam("key") String key) throws InvalidKeyException, UnknownKeyException, IOException {
        return db.deleteProperty(key);
    }

    @PREPEND
    public String prepend(@PathParam("key") String key, String preText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, preText + db.readProperty(key));
        }
    }

    @APPEND
    public String append(@PathParam("key") String key, String postText)
            throws InvalidKeyException, UnknownKeyException, IOException, InvalidValueException {
        synchronized (db) {
            return db.updateProperty(key, db.readProperty(key) + postText);
        }
    }
}
