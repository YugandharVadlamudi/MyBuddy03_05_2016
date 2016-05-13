package com.example.kiran.mybuddy.Utils;

import java.io.Serializable;

/**
 * Created by Kiran on 26-04-2016.
 */
public class Data implements Serializable {
    private String imagePath;
    private String name;
    private String phonenumber;
    private String bloodGroup;
    private String email;
    private String age;
    private String profession;
    private String working;
    private String city;
    private String state;
    private String country;
    private String favourit;
    private String id;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFavourit(String favourit) {
        this.favourit = favourit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    public String getWorking() {
        return working;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getFavourit() {
        return favourit;
    }

    public String getId() {
        return id;
    }
}
