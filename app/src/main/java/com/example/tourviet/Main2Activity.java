package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    Button creatTourBtn;
    Button userProfileBtn;
    Button viewAvailableTourBtn;

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
            image_url = bundle.getString("Key_3");
        }
    }

    private void Anhxa() {
        userProfileBtn = findViewById(R.id.main2_viewUserProfileBtn);
        creatTourBtn = findViewById(R.id.main2_createTourBtn);
        viewAvailableTourBtn = findViewById(R.id.main2_viewAvailableTourBtn);
    }


    private void setOnClickEvent() {

        creatTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, TourCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                bundle.putString("Key_3", image_url);
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
                bundle.putString("Key_3", image_url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        viewAvailableTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, TourAvailableActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                bundle.putString("Key_3", image_url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}
