package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCommentActivity extends AppCompatActivity {

    String token;
    long id;
    UserInfoGET userInfo;

    EditText content;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        setupVariable();
        findView();
        getUserInfo();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });
    }

    private void setupVariable() {
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        id = intent.getLongExtra("id", 0);
    }

    private void findView() {
        content = findViewById(R.id.addComment_content);
        submitBtn = findViewById(R.id.addComment_submitBtn);
    }

    private void getUserInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<UserInfoGET> call = jsonPlaceHolderApi.userInfo(token);
        call.enqueue(new Callback<UserInfoGET>() {
            @Override
            public void onResponse(Call<UserInfoGET> call, Response<UserInfoGET> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                } else {
                    userInfo = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserInfoGET> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitComment() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Object> call = jsonPlaceHolderApi.SendComment(new CommentBody(id, userInfo.getId(), content.getText().toString()), token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
