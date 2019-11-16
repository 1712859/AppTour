package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface userClient {
   @POST("/user/register")
   Call<User> CreateAccount(@Body User user);

   @POST("/user/login")
   Call<LoginClient> LoginAccount(@Body LoginClient loginClient);
}
