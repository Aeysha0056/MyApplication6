package com.example.aeiys.myapplication6;

public class Patient {

    public String Name , diseaseDescription ,age,gender;


    public Patient (){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Patient(String name, String diseaseDescription, String age /*,String gender*/) {
        Name = name;
        this.diseaseDescription = diseaseDescription;
        this.age = age;
       // this.gender = gender;
    }
}
