package com.example.tourviet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface userClient {
   @POST("/user/register")
   Call<User> CreateAccount(@Body User user);

   @POST("/user/login")
   Call<LoginClient> LoginAccount(@Body LoginClient loginClient);

   @POST("/user/login/by-facebook")
   Call<FacebookLogin> LoginFacebook(@Body FacebookLogin  facebookLogin);

   @GET("/user/info")
   Call<User_infor> GetUserInfor(@Header("Authorization") String loginToken);

   @POST("/user/edit-info")
   Call<User_infor> UppdateUserInfor(@Header("Authorization") String Token,@Body User_infor user_infor) ;

   @POST("/user/update-password")
   Call<Pass> UppdatePass(@Header("Authorization")String Token,@Body Pass pass);
   @Multipart
   @POST("/user/update-avatar")
   Call<Avatar> UppdateAvatar(@Header("Authorization")String Token, @Body Avatar avatar);

//   @GET("/tour/history-user")
//   Call<TourListGet> getUserTour(@Header("Authorization") String Token,@Body User_tour usertour);


}
