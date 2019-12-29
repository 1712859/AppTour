package com.example.tourviet;

public class verify_otp_recovery {
    int userId;
    String newPassword;
    String verifyCode;

    public verify_otp_recovery(int userId, String newPassword, String verifyCode) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.verifyCode = verifyCode;
    }
}
