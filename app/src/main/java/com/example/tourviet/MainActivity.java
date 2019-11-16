package com.example.tourviet;
import com.example.tourviet.api.userClient;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourviet.api.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editUser,editPass;
    Button buttonDangKi,buttonDangNhap;
    String Pass,Email;
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
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dangki);
                final EditText editAddressdk = (EditText)dialog.findViewById(R.id.dangki_Address);
                final EditText editPassdk = (EditText)dialog.findViewById(R.id.dangki_Pass);
                final EditText editEmaildk = (EditText)dialog.findViewById(R.id.dangki_email);
                final EditText editHo_tendk = (EditText)dialog.findViewById(R.id.dangki_Ho_Ten);
                final EditText editSDTdk = (EditText)dialog.findViewById(R.id.dangki_SDT);
                final EditText editDatedk = (EditText)dialog.findViewById(R.id.dangki_Date) ;
                final EditText editPhaidk = (EditText)dialog.findViewById(R.id.dangki_Phai) ;
                final Button buttonDangKi = (Button)dialog.findViewById(R.id.buttonDangKi_dangki);
                Button buttonHuy = (Button)dialog.findViewById(R.id.buttonDangKi_huy);
                buttonDangKi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(editAddressdk.getText().length()!=0 && editPassdk.getText().length()!=0 && editEmaildk.getText().length()!=0 && editSDTdk.getText().length()!=0 && editHo_tendk.getText().length()!=0 && editDatedk.getText().length()!=0 && editPhaidk.getText().length()!=0) {
                            editUser.setText(editEmaildk.getText().toString().trim());
                            editPass.setText(editPassdk.getText().toString().trim());
                            User user=new User(

                                    editPassdk.getText().toString().trim(),
                                    editHo_tendk.getText().toString().trim(),
                                    editEmaildk.getText().toString().trim(),
                                    editSDTdk.getText().toString().trim(),
                                    editAddressdk.getText().toString().trim(),
                                    editDatedk.getText().toString().trim(),
                                    Integer.parseInt(editPhaidk.getText().toString().trim())
                            );
                            sendNetworkRequest(user);

                        }
                        else
                            Toast.makeText(MainActivity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();

                    }
                    private void sendNetworkRequest(User user)
                    {
                        Retrofit builder= new Retrofit.Builder()
                                .baseUrl("http://35.197.153.192:3000")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        userClient client =builder.create((userClient.class));
                        Call<User> call = client.CreateAccount(user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(MainActivity.this,"Đăng ký thành công"+response.body().getId() ,Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(MainActivity.this,"Đăng ký chưa thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                buttonHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });
                dialog.show();
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().length()!=0 && editPass.getText().length()!=0)
                {
                    if(editUser.getText().toString().equals(Email) && editPass.getText().toString().equals(Pass))
                    {
                        Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);

                    }else
                    if(editUser.getText().toString().equals("admin") && editPass.getText().toString().equals("admin"))
                    {
                        Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);

                    }else
                        Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thất bại",Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(MainActivity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();

                }
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

