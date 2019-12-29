package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("/tour/history-user")
    Call<TourListGet> getTourUserList(@Header("Authorization") String loginToken, @Body User_tour usertour);

    /////////////////////////////////////////////////

    @GET("/tour/list")
    Call<TourListGet> getTourList(@Query("pageNum") int index, @Query("rowPerPage") int size, @Header("Authorization") String loginToken);

    @POST("/tour/create")
    Call<TourItem> postTourCreateForm(@Body TourCreateForm form, @Header("Authorization") String loginToken);

    @GET("/tour/history-user")
    Call<UserTour> getUserTourList(@Query("pageIndex") int index, @Query("pageSize") int size, @Header("Authorization") String token);

    @GET("/tour/info")
    Call<TourInfo> InfoTour(@Query("tourId") long id, @Header("Authorization") String token);

    @POST("/tour/update-tour")
    Call<TourUpdate> UpdateTour(@Body TourUpdate cloneTour, @Header("Authorization") String token);

    @POST("/tour/set-stop-points")
    Call<Object> addStopPoint(@Body AddStopPointBody addStopPointBody, @Header("Authorization") String token);

    @GET("/user/info")
    Call<UserInfoGET> userInfo(@Header("Authorization") String token);
}
