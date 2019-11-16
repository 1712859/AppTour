package com.example.tourviet.api;

public class User {

    private Integer id;
   private String password;
   private String fullName;
   private String email;
    private String phone;
    private String address;
    private String  dob;
    private int gender;

    public User(String password, String fullName, String email, String phone, String address, String dob, int gender) {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }
}
