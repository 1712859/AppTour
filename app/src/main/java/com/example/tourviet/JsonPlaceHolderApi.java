package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("tour/list")
    Call<TourListGet> getTourList(@Header("Authorization") String loginToken);

    @GET("/tour/history-user")
    Call<TourListGet> getTourUserList(@Header("Authorization") String loginToken,@Body User_tour usertour);

    @POST("tour/create")
    Call<TourCreateForm> postTourCreateForm(@Body TourCreateForm form, @Header("Authorization") String loginToken);

}
