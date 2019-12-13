package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;

public class Main2Activity extends AppCompatActivity {

    Button creatTourBtn;
    Button userProfileBtn;
    Button viewAvailableTourBtn;
    Button LogOut;
    String token, image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();
        setOnClickEvent();

        // bắt token từ đăng nhập
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //...............................
        if (bundle != null) {
            token = bundle.getString("Key_1");
        }
    }

    private void Anhxa() {
        userProfileBtn = findViewById(R.id.main2_viewUserProfileBtn);
        creatTourBtn = findViewById(R.id.main2_createTourBtn);
        viewAvailableTourBtn = findViewById(R.id.main2_viewAvailableTourBtn);
        LogOut = findViewById(R.id.main2_Đăng_Xuất);
    }


    private void setOnClickEvent() {
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                LoginManager.getInstance().logOut();
                startActivity(intent);
            }
        });

        creatTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, TourCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Main2Activity_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        viewAvailableTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, TourAvailableActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

    }

}
