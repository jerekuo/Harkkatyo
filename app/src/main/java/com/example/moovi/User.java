package com.example.moovi;

import java.util.Date;
import java.util.Random;

public class User {
    String password; //käytetään stringi password olion kautta
    String age;
    String email;
    String firstName;
    String lastName;
    int userId;

    public int randNum() {
        Random random = new Random();

        return random.nextInt(50000);
    }


    //GETSETS
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public User() {

    }
    //CONSTRUCTOR
    public User(String password, String email, String firstName, String lastName, String age) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userId = randNum();

    }


}
