package com.example.moovi;

import java.util.Date;
import java.util.Random;

public class User {
    String birthdate;
    String email;
    String firstName;
    String lastName;
    String address;
    String phoneNumber;

    //GETSETS

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String age) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public User() {

    }
    //CONSTRUCTOR
    public User(String email, String firstName, String lastName, String birthDate, String address, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }


}
