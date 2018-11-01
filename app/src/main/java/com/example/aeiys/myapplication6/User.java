package com.example.aeiys.myapplication6;

public class User {

    public String name ,email;

    public User (){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
