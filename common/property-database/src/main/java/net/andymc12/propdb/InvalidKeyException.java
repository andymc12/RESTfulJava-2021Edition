package net.andymc12.propdb;

public class InvalidKeyException extends Exception {

    public InvalidKeyException(String key) {
        super(key);
    }
}
