package net.andymc12.propdb;

public class DuplicateKeyException extends Exception {

    public DuplicateKeyException(String key) {
        super(key);
    }
}
