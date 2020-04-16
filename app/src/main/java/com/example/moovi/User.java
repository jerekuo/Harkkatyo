package com.example.moovi;

import java.util.Date;

public class User {
    String password; //käytetään stringi password olion kautta
    Date age;
    String email;
    String firstName;
    String lastName;
    int userId;





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
    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
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


    //CONSTRUCTOR
    User(String password, String email, String firstName, String lastName, Date age) {
        this.password = password;
        this.email = email;
        this.firstName = email;
        this.lastName = lastName;
        this.age = age;
    }


}
