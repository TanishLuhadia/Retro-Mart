package com.thinking.machines.retro.dto;
import java.util.*;
public class BikeDTO
{
private int id;
private String model;
private String brand;
private  String numberOfOwner;
private String state;
private String city;
private int year;
private String fuel;
private int driven;
private String conditions;
private String features;
private Double price;
private String contactNumber;
private String email;
private Date date;
private String product;
public BikeDTO()
{
}
public void setDate(Date date)
{
this.date=date;
}
public Date getDate()
{
return this.date;
}
public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setNumberOfOwner(String numberOfOwner) {
        this.numberOfOwner = numberOfOwner;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }




    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDriven(int driven) {
        this.driven = driven;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }


    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setContactNumber(String contact_number) {
        this.contactNumber = contact_number;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    public int getDriven() {
        return driven;
    }

    public String getConditions() {
        return conditions;
    }


    public String getFuel() {
        return fuel;
    }



    public String getFeatures() {
        return features;
    }

    public String getEmail() {
        return email;
    }


    public String getContactNumber() {
        return contactNumber;
    }



    public String getBrand() {
        return this.brand;
    }
    public String getNumberOfOwner() {
        return this.numberOfOwner;
    }
    public String getState() {
        return this.state;
    }
    public String getCity() {
        return this.city;
    }

    public void setProduct(String product) {
        this.product=product;
    }
    public String getProduct() {
        return this.product;
    }




}