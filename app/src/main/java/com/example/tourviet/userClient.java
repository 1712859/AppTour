package com.example.tourviet;

import android.widget.ScrollView;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

   @POST("/user/update-avatar")
   Call<Avatar> UppdateAvatar(@Header("Authorization")String Token, @Body String file);

   @POST("/user/request-otp-recovery")
   Call<ForgotPass> forgotPass(@Body ForgotPass forgotPass);

   @HTTP(method = "GET", path = "/user/search", hasBody = true)
   Call<Userlistget> searchUser(@Query("searchKey") Userlistget userlistget, @Query("pageIndex") int index, @Query("pageSize") String size);

   @POST("/user/verify-otp-recovery")
   Call<verify_otp_recovery> requet_otp(@Body verify_otp_recovery verifyOtpRecovery);

   @POST("/tour/add/member")
   Call<add_member_class> addmember(@Header("Authorization")String Token,@Body add_member_class add_member );

}
