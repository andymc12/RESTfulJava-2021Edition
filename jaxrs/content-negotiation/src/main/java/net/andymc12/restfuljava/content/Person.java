package net.andymc12.restfuljava.content;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
public class Person {
    private String lastName;
    private String firstName;
    private int age;
    private Color favoriteColor;

    public Person() {
    }

    public Person(String lastName, String firstName, int age, Color favoriteColor) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.favoriteColor = favoriteColor;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public int getage() {
        return age;
    }
    public void setage(int age) {
        this.age = age;
    }
    public Color getFavoriteColor() {
        return favoriteColor;
    }
    public void setFavoriteColor(Color favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    @Override
    public String toString() {
        return String.format("%s, %s Age: %d, Favorite Color: %s",
                            lastName, firstName, age, favoriteColor);
    }
}