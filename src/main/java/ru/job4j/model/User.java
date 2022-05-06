package ru.job4j.model;

public class User {
    private int id;

    private String name;

    private String surname;

    private Address address;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(int id, String name, String surname, Address address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /* Constructors, getters, setters */


}
