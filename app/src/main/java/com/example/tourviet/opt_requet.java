package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class opt_requet extends AppCompatActivity {
    EditText Pass,OTP;
    Button Oke;
    String email_user;
    Long id;
    List<gest_user> userData = new ArrayList<>();
    gest_user User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt_requet);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //...............................
        if (bundle != null) {
            email_user = bundle.getString("email_User");
        }
        Userlistget userlistget = new Userlistget(
                email_user
        );
        Retrofit builder= new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userClient client =builder.create((userClient.class));
        final Call<Userlistget> call= client.searchUser(userlistget.searchKey,1,10);
        call.enqueue(new Callback<Userlistget>() {
            @Override
            public void onResponse(Call<Userlistget> call, Response<Userlistget> response) {
            Userlistget user = response.body();
            userData.clear();
                final boolean b = userData.addAll(user.getUsers());
                if(b==false)
                {
                    Toast.makeText(opt_requet.this, "Email hay SDT không đúng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                    return;
                };
                userData.clear();
            userData.addAll(user.getUsers());
            User = userData.get(0);
                id = User.getId();
            }

            @Override
            public void onFailure(Call<Userlistget> call, Throwable t) {


            }
        });

        Oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_otp_recovery verifyOtpRecovery = new verify_otp_recovery(
                        id,
                        Pass.getText().toString(),
                        OTP.getText().toString()
                );
                Retrofit builder= new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userClient client =builder.create((userClient.class));
                Call<verify_otp_recovery> call = client.requet_otp(verifyOtpRecovery);
                call.enqueue(new Callback<verify_otp_recovery>() {
                    @Override
                    public void onResponse(Call<verify_otp_recovery> call, Response<verify_otp_recovery> response) {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(opt_requet.this,MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<verify_otp_recovery> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "lỗi....", Toast.LENGTH_LONG).show();
                    }
                });
                }
        });


    }

    private void Anhxa() {
        Pass = (EditText)findViewById(R.id.otp_pass);
        OTP = (EditText)findViewById(R.id.otp_otp);
        Oke = (Button)findViewById(R.id.otp_button);

    }
}
