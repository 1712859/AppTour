package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity_EditImformation extends AppCompatActivity {
    Button Backbutton,SaveInformationButton;
    String token,image_url;
    EditText name,email,phone,date,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__edit_imformation);
        Anhxa();
        controlbutton();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            token = bundle.getString("Key_1");
            image_url = bundle.getString("Key_3");
        }
        Run();

    }
    private  void Run()
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
                    Toast.makeText(MainActivity_EditImformation.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

                    return;
                }
                // chuyển tên thành chữ in hoa
                StringBuilder sb = new StringBuilder(response.body().getFullName());
                for (int index = 0; index < sb.length(); index++) {
                    char c = sb.charAt(index);
                    if (Character.isLowerCase(c)) {
                        sb.setCharAt(index, Character.toUpperCase(c));
                    } else {
                        sb.setCharAt(index, Character.toLowerCase(c));
                    }
                }
                //......................................................

                name.setText(response.body().getFullName().toUpperCase());
                email.setText(response.body().getEmail());
                phone.setText(response.body().getPhone());
                date.setText(response.body().getDob());
                if(response.body().getGender()==1)
                {
                    gender.setText("Male");
                }
                else gender.setText("Female");
            }

            @Override
            public void onFailure(retrofit2.Call<User_infor> call, Throwable t) {
                Toast.makeText(MainActivity_EditImformation.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void controlbutton () {

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_EditImformation.this, Main2Activity_user.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                bundle1.putString("Key_3", image_url);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
        });
        SaveInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_infor user_inforU = new User_infor(
                        name.getText().toString().trim(),
                        email.getText().toString().trim(),
                        phone.getText().toString().trim(),
                        Integer.parseInt(gender.getText().toString()),
                        date.getText().toString().trim()


                );
                updateinfor(user_inforU,token);



            }
        });
    }
        private void updateinfor(User_infor user_infor,String token1)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://35.197.153.192:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            userClient client = retrofit.create((userClient.class));
            Call<User_infor> call = client.UppdateUserInfor(token1,user_infor);
            call.enqueue(new Callback<User_infor>() {
                @Override
                public void onResponse(Call<User_infor> call, Response<User_infor> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(MainActivity_EditImformation.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        Intent intent = new Intent(MainActivity_EditImformation.this, Main2Activity_user.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Key_1", token);
                        intent.putExtras(bundle1);
                        bundle1.putString("Key_3", image_url);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<User_infor> call, Throwable t) {
                    Toast.makeText(MainActivity_EditImformation.this, "lỗi từ server.", Toast.LENGTH_LONG).show();

                }
            });
        }

        private void Anhxa() {

        Backbutton = (Button)findViewById(R.id.backgeED);
        name =(EditText)findViewById(R.id.profile_nameED);
        email =(EditText)findViewById(R.id.profile_email_valueED);
        phone =(EditText)findViewById(R.id.profile_SDT_valueED);
        gender =(EditText)findViewById(R.id.profile_gender_valueED);
        date =(EditText)findViewById(R.id.profile_date_valueED);
        SaveInformationButton = (Button) findViewById((R.id.buttonSaveEditInfor));


    }
}
