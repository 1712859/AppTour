package com.example.tourviet;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editUser,editPass;
    Button buttonDangKi,buttonDangNhap;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        controlButton();
    }
    private void controlButton() {

        buttonDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main1Activity.class);
                startActivity(intent);
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().length()!=0 && editPass.getText().length()!=0)
                {
                    LoginClient loginClient =new LoginClient(
                            editUser.getText().toString().trim(),
                            editPass.getText().toString().trim()
                    );
                    sendNetworkRequestLogin(loginClient);


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

        //        button đăng nhập bằng google và facebook
        //        ..........
    }

    private void Anhxa() {
        editUser = (EditText)findViewById(R.id.edittextuser);
        editPass = (EditText)findViewById(R.id.edittextpass);
        buttonDangKi = (Button)findViewById((R.id.buttonDangKi));
        buttonDangNhap = (Button)findViewById(R.id.buttonDangNhap);
//        button đăng nhập bằng google và facebook
//         .........................

    }

}

