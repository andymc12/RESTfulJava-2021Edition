package net.andymc12.propdb;

public class UnknownKeyException extends Exception {

    public UnknownKeyException(String key) {
        super(key);
    }
}
