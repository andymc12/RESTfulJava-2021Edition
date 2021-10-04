package net.andymc12.propdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PropsDB implements AutoCloseable {
    static final Map<Path, PropsDB> instances = new ConcurrentHashMap<>();
    final Path propsFile;

    /**
     * Get the Props instance for the specified properties file.
     * 
     * @param filename - path (absolute or relative) to properties file.
     * @return instance of PropsDB associated with that file - creating a new one if necessary.
     * @throws IOException if file specified by filename parameter cannot be found or created.
     */
    public static PropsDB getDB(String filename) throws IOException {
        Path absPath = Paths.get(filename).toAbsolutePath();
        if (!Files.exists(absPath)) {
            Files.createFile(absPath);
        }
        return instances.computeIfAbsent(absPath, k -> new PropsDB(k));
    }

    private PropsDB(Path propsFile) {
        this.propsFile = propsFile;
    }

    public synchronized void createProperty(String key, String value) throws DuplicateKeyException, InvalidKeyException, InvalidValueException, IOException {
        checkValidKey(key);
        checkValidValue(value);

        try (Props props = new Props(propsFile)) {
            if (null != props.putIfAbsent(key, value)) {
                throw new DuplicateKeyException(key);
            }
        }
    }

    public synchronized String readProperty(String key) throws InvalidKeyException, UnknownKeyException, IOException {
        checkValidKey(key);

        try (Props props = new Props(propsFile)) {
            String value = props.getProperty(key);
            if (value == null) {
                throw new UnknownKeyException(key);
            }
            return value;
        }
    }

    public synchronized String updateProperty(String key, String value)
            throws InvalidKeyException, InvalidValueException, UnknownKeyException, IOException {
        checkValidKey(key);
        checkValidValue(value);

        try (Props props = new Props(propsFile)) {
            if (!props.containsKey(key)) {
                throw new UnknownKeyException(key);
            }
            return (String) props.put(key, value);
        }
    }

    public synchronized String deleteProperty(String key) throws InvalidKeyException, UnknownKeyException, IOException {
        checkValidKey(key);

        try (Props props = new Props(propsFile)) {
            String value = (String) props.remove(key);
            if (value == null) {
                throw new UnknownKeyException(key);
            }
            return value;
        }
    }

    private void checkValidKey(String key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException(null);
        }
    }

    private void checkValidValue(String value) throws InvalidValueException {
        if (value == null) {
            throw new InvalidValueException(null);
        }
    }
    @Override
    public void close() throws Exception {
        instances.remove(propsFile);
    }
}
