package com.example.tourviet;

public class LoginClient {
    private String userId;
    private String token;
    private String emailPhone;
    private String password;

    public LoginClient(String emailPhone, String password) {
        this.emailPhone = emailPhone;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

}
