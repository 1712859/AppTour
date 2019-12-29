package com.example.tourviet;

public class gest_user {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private int gender;
    private String dob;
    private String avatar;
    private int typeLogin;

    public gest_user(int id, String fullName, String email, String phone, int gender, String dob, String avatar, int typeLogin) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.avatar = avatar;
        this.typeLogin = typeLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(int typeLogin) {
        this.typeLogin = typeLogin;
    }
}
