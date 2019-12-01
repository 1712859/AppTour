package com.example.tourviet;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tourviet.FacebookLogin;
import com.example.tourviet.userClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import com.example.tourviet.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.AccessibleObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editUser,editPass;
    Button buttonDangKi,buttonDangNhap;
    String token,tokenA;
    String image_url;
    LoginButton facebookbutton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginManager.getInstance().logOut();
        Anhxa();
        controlButton();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void controlButton() {

        buttonDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main1Activity.class);
                startActivity(intent);
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(editUser.getText().length()!=0 && editPass.getText().length()!=0)
                {
                    LoginClient loginClient =new LoginClient(
                            editUser.getText().toString().trim(),
                            editPass.getText().toString().trim()
                    );



                    if(editUser.getText().toString().equals("admin") && editPass.getText().toString().equals("admin"))
                    {
                        Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();



                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);

                    }else {
                        sendNetworkRequestLogin(loginClient);
                    }
                }else
                {
                    Toast.makeText(MainActivity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();

                }
            }

            private void sendNetworkRequestLogin(LoginClient loginClient) {

                Retrofit builder= new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userClient client =builder.create((userClient.class));
                Call<LoginClient> call = client.LoginAccount(loginClient);
                call.enqueue(new Callback<LoginClient>() {
                    @Override
                    public void onResponse(Call<LoginClient> call, Response<LoginClient> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Tài khoản chưa đăng kí", Toast.LENGTH_LONG).show();
                            return;
                        }else
                        {
                            // trả biến Authorization
                            token =response.body().getToken();
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công. xin chào "+response.body().getUserId(), Toast.LENGTH_LONG).show();

                            //mở Acctivity mới - truyền token qua Main2Activity
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Key_1", token);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginClient> call, Throwable t) {

                    }
                });
            }
        });
        callbackManager = CallbackManager.Factory.create();
        facebookbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                if(AccessToken.getCurrentAccessToken()==null)
                {
                    Toast.makeText(MainActivity.this, "Không lấy được thông tin đăng nhập", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    try {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        try {

                                            String fb_id = object.getString("id");   //FaceBook User ID
                                            image_url = "https://graph.facebook.com/" + fb_id + "/picture?width=200&height=200";
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,picture.type(small)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    } catch (Exception e) {

                    }

                    FacebookLogin facebookLogin = new FacebookLogin(
                            loginResult.getAccessToken()
                                    .getToken());

                    sendNetworkRequestLoginFB(facebookLogin);
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "đăng nhập fb không thành công", Toast.LENGTH_LONG).show();

            }
            private void sendNetworkRequestLoginFB(FacebookLogin facebookLogin)
            {
                Retrofit builder= new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userClient client =builder.create((userClient.class));
                Call<FacebookLogin> call = client.LoginFacebook(facebookLogin);
                call.enqueue(new Callback<FacebookLogin>() {
                    @Override
                    public void onResponse(Call<FacebookLogin> call, Response<FacebookLogin> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "lỗi ko gửi yêu cầu qua sever được", Toast.LENGTH_LONG).show();
                            return;
                        }else
                        {
                            tokenA = response.body().getToken();
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công. xin chào " + response.body().getFullName(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Key_1", tokenA);
                            intent.putExtras(bundle);
                            bundle.putString("Key_3", image_url);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<FacebookLogin> call, Throwable t) {

                    }
                });

            }
        });

        //        button đăng nhập bằng google và facebook
        //        ..........
    }

    private void Anhxa() {
        editUser = (EditText)findViewById(R.id.edittextuser);
        editPass = (EditText)findViewById(R.id.edittextpass);
        buttonDangKi = (Button)findViewById((R.id.buttonDangKi));
        buttonDangNhap = (Button)findViewById(R.id.buttonDangNhap);
        facebookbutton = (LoginButton) findViewById(R.id.buttonDangNhapFB);



    }


}

