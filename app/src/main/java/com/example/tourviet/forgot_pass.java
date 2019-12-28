package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class forgot_pass extends AppCompatActivity {
    Spinner spinner;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass2);
        Anhxa();

        String[] items = new String[]{"email","phone"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(forgot_pass.this,
                R.layout.spinner_item,items);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ForgotPass forgotPass = new ForgotPass(
                       spinner.getSelectedItem().toString(),
                       editText.getText().toString()
            );

                Retrofit builder= new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userClient client =builder.create((userClient.class));
                Call<ForgotPass> call = client.forgotPass(forgotPass);
                call.enqueue(new Callback<ForgotPass>() {
                    @Override
                    public void onResponse(Call<ForgotPass> call, Response<ForgotPass> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(forgot_pass.this, "Email hay SDT không đúng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            Toast.makeText(forgot_pass.this, "gửi thành công ("+ response.body().getExpiredOn()+")", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgot_pass.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPass> call, Throwable t) {
                        Toast.makeText(forgot_pass.this,"lỗi...",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void Anhxa() {
        spinner = (Spinner)findViewById(R.id.QuenMK_spinner1);
        editText =  (EditText)findViewById(R.id.QuenMK_Email);
        button = (Button)findViewById(R.id.QuenMK_Send);
    }
}
