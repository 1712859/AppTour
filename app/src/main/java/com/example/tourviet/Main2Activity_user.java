package com.example.tourviet;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity_user extends AppCompatActivity {
    Button Backbutton,EditInformationButton,MyTour,avatar;
    String token,image_url;
    TextView name,email,phone,address,date,gender,changePass;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_user);

        Anhxa();
        controlbutton();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            token = bundle.getString("Key_1");

        }
        Run();

    }
    private  void Run()
    {
//
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
                    Toast.makeText(Main2Activity_user.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

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
                Picasso.get().load(response.body().getAvatar()).fit().into(image);
                name.setText(convert(response.body().getFullName()));
                email.setText(response.body().getEmail());
                phone.setText(response.body().getPhone());
                address.setText(response.body().getAddress());
                date.setText(response.body().getDob());
                if(response.body().getGender()==1)
                {
                    gender.setText("Male");
                }
                else
                if(response.body().getGender()==0)
                {
                    gender.setText("Female");
                }else
                    gender.setText("Unknow");
                image_url = response.body().getAvatar();
            }

            @Override
            public void onFailure(retrofit2.Call<User_infor> call, Throwable t) {
                Toast.makeText(Main2Activity_user.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private String convert (String string)
    {
        String kq;
        StringBuilder sb = new StringBuilder(string);
        for (int index = 0; index < sb.length(); index++) {
            char c = sb.charAt(index);
            if (Character.isLowerCase(c)) {
                sb.setCharAt(index, Character.toUpperCase(c));
            }
        }
        return  kq = sb.toString();
    }
    private void controlbutton (){

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity_user.this,Main2Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
        });
        EditInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity_user.this,MainActivity_EditImformation.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
        });
        MyTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity_user.this,MainActivity_UserTour.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);

                startActivity(intent);
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity_user.this,Main3Activity_ChangePass.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                bundle1.putString("Key_3", image_url);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity_user.this,Main3Activity_changeAvatar.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                bundle1.putString("Key_3", image_url);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }
    private void Anhxa() {

        Backbutton = (Button)findViewById(R.id.backge);
        name =(TextView)findViewById(R.id.profile_name);
        email =(TextView)findViewById(R.id.profile_email_value);
        phone =(TextView)findViewById(R.id.profile_SDT_value);
        gender =(TextView)findViewById(R.id.profile_gender_value);
        date =(TextView)findViewById(R.id.profile_date_value);
        address =(TextView)findViewById(R.id.profile_address_value);
        image = (ImageView)findViewById(R.id.profile_pic);
        EditInformationButton = (Button)findViewById((R.id.buttonEditImformation));
        MyTour = (Button)findViewById(R.id.buttonviewMyTour);
        changePass = (TextView)findViewById(R.id.changePass);
        avatar = (Button) findViewById(R.id.buttonAvata);
    }
}
