package com.example.tourviet;

public class FacebookLogin {
    private String avatar;
    private  String fullName;
    private  String token;
    private String accessToken;


    public FacebookLogin(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public String getToken() {
        return token;
    }
}
