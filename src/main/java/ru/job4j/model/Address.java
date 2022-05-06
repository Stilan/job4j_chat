package ru.job4j.model;

public class Address {

    private int id;

    private String country;

    private String city;

    private String street;

    private String house;

    /* Constructors, getters, setters */

    public Address(int id, String country, String city, String street, String house) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
