package net.andymc12.restfuljava.content;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorObject {
    private String message;
    private Integer id;
    public ErrorObject() {
    }

    public ErrorObject(String message, Integer id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(message, id);
    }
}