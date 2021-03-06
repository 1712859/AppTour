package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main1Activity extends AppCompatActivity {
    EditText editAddressdk,editPassdk,editEmaildk,editHo_tendk,editSDTdk,editDatedk;
    Button buttonDangKi,buttonHuy;
    Spinner editgender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Anhxa();
        controlButton();
        String[] items = new String[]{"Nữ","Nam"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main1Activity.this,
                R.layout.spinner_item,items);
        editgender.setAdapter(adapter);
    }

    private void controlButton() {

        buttonDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editAddressdk.getText().length()!=0 && editPassdk.getText().length()!=0 && editEmaildk.getText().length()!=0 && editSDTdk.getText().length()!=0 && editHo_tendk.getText().length()!=0 && editDatedk.getText().length()!=0) {
                    String genderterm = "0";
                    String text = editgender.getSelectedItem().toString();
                    if(text=="Nam")
                    {
                        genderterm="1";
                    }
                    User user=new User(

                            editPassdk.getText().toString().trim(),
                            editHo_tendk.getText().toString().trim(),
                            editEmaildk.getText().toString().trim(),
                            editSDTdk.getText().toString().trim(),
                            editAddressdk.getText().toString().trim(),
                            editDatedk.getText().toString().trim(),
                            Integer.parseInt(genderterm)
                    );
                    sendNetworkRequest(user);

                }
                else
                    Toast.makeText(Main1Activity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();
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
                        if (!response.isSuccessful()) {
                            Toast.makeText(Main1Activity.this, "Email hay SDT đã có người sử dụng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            Toast.makeText(Main1Activity.this, "Đăng ký thành công với Id: " + response.body().getId(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Main1Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Main1Activity.this,"Đăng ký chưa thành công",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }) ;
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main1Activity.this,MainActivity.class);
                startActivity(intent);

            }
        });



    }
    private void Anhxa() {
        editAddressdk = (EditText)findViewById(R.id.dangki_Address);
        editPassdk = (EditText)findViewById(R.id.dangki_Pass);
        editEmaildk = (EditText)findViewById(R.id.dangki_email);
        editHo_tendk = (EditText)findViewById(R.id.dangki_Ho_Ten);
        editSDTdk = (EditText)findViewById(R.id.dangki_SDT);
        editDatedk = (EditText)findViewById(R.id.dangki_Date) ;
        editgender = (Spinner)findViewById(R.id.spinner1);
        buttonDangKi = (Button)findViewById(R.id.buttonDangKi_dangki);
        buttonHuy = (Button)findViewById(R.id.buttonDangKi_huy);
//        button đăng nhập bằng google và facebook
//         .........................

    }
}
