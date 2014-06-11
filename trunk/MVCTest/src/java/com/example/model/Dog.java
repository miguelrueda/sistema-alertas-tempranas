package com.example.model;

/**
 *
 * @author t41507
 */
public class Dog implements java.io.Serializable {

    private String breed;

    public Dog(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}
