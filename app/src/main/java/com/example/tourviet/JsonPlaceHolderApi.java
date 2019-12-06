package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("tour/list")
    Call<TourListGet> getTourList(@Header("Authorization") String loginToken);

    @POST("tour/create")
    Call<TourCreateForm> postTourCreateForm(@Body TourCreateForm form, @Header("Authorization") String loginToken);

}
