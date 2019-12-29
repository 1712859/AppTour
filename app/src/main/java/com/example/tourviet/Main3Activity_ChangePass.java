package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity_ChangePass extends AppCompatActivity {

    EditText New,Old;
    Button Back,save;
    String token,image_url;
    ImageView image;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3__change_pass);
        Anhxa();
        controlbutton();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            token = bundle.getString("Key_1");
            image_url = bundle.getString("Key_3");
        }
        Picasso.get().load(image_url).fit().into(image);
    }

    private void controlbutton() {
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity_ChangePass.this,Main2Activity_user.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://35.197.153.192:3000")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    userClient client = retrofit.create((userClient.class));

                    Call<User_infor> call = client.GetUserInfor(token);
                    call.enqueue(new Callback<User_infor>() {
                        @Override
                        public void onResponse(retrofit2.Call<User_infor> call, Response<User_infor> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(Main3Activity_ChangePass.this, "lỗi lấy ID.", Toast.LENGTH_LONG).show();

                                return;
                            }
                            ID = response.body().getId();
                        }

                        @Override
                        public void onFailure(retrofit2.Call<User_infor> call, Throwable t) {
                            Toast.makeText(Main3Activity_ChangePass.this, "lỗi lấy ID", Toast.LENGTH_LONG).show();
                        }
                    });
                }


                Pass pass= new Pass(
                        ID,
                        Old.getText().toString().trim(),
                        New.getText().toString().trim()
                );
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                userClient client = retrofit.create((userClient.class));

                Call<Pass> call = client.UppdatePass(token,pass);
                call.enqueue(new Callback<Pass>() {
                    @Override
                    public void onResponse(Call<Pass> call, Response<Pass> response) {
                        if (!response.isSuccessful()) {
                                    Toast.makeText(Main3Activity_ChangePass.this,response.message(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(Main3Activity_ChangePass.this,response.message() , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Main3Activity_ChangePass.this,Main2Activity_user.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Key_1", token);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Pass> call, Throwable t) {
                        Toast.makeText(Main3Activity_ChangePass.this, "lỗi lấy thông tin từ server." , Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    private void Anhxa() {
        New = (EditText)findViewById(R.id.newPass);
        Old = (EditText)findViewById(R.id.oldPass);
        Back = (Button)findViewById(R.id.backge);
        save = (Button)findViewById(R.id.SaveButton);
        image = (ImageView)findViewById(R.id.profile_pic);


    }
}

