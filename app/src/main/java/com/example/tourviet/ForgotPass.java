package com.example.tourviet;

public class ForgotPass {
    String type;
    String value;
    String expiredOn;

    public ForgotPass(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getExpiredOn() {
        return expiredOn;
    }
}
