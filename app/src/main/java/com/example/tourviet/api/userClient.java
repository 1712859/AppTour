package com.example.tourviet.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface userClient {
   @POST("/user/register")
   Call<User> CreateAccount(@Body User user);
}
