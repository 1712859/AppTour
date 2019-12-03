package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity_UserTour extends AppCompatActivity {


    Button Backbutton;
    String token,image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__user_tour);
        Anhxa();
        controlbutton();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //...............................
        if (bundle != null) {
            token = bundle.getString("Key_1");
            image_url = bundle.getString("Key_3");
        }

    }


    private void controlbutton (){

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_UserTour.this,Main2Activity_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                bundle.putString("Key_3", image_url);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }
    private void Anhxa() {

        Backbutton = (Button)findViewById(R.id.backge);
    }
}
