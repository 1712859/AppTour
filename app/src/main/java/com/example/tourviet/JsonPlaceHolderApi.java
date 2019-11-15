package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JsonPlaceHolderApi {

    @GET("tour/list")
    Call<TourListGet> getTourList(@Header("Authorization") String loginToken);

}
