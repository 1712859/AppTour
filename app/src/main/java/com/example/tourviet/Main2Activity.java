package com.example.tourviet;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    TourAdapter tourAdapter;
    ListView tourListView;
    Button Backbutton,creattour;
    List<TourItem> tourData = new ArrayList<TourItem>();

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();
        controlbutton();

        // bắt token từ đăng nhập
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //...............................
        if (bundle != null) {
            token = bundle.getString("Key_1");
        }
        tourData.add(new TourItem(99, 0, "Đi hạ long (sample)", 100000, 200000, "21/10/1999", "27/2/2014", 0, 0, false, "https://tour.dulichvietnam.com.vn/uploads/tour/1554713265_tour-ha-long-3.jpg"));
        tourData.add(new TourItem(66, 0, "Đi đà lạc (sample)", 700000, 900000, "14/5/216", "8/2/374", 0, 0, false, "https://cdn3.ivivu.com/2013/09/khu-nghi-duong-terracotta-da-lat-1-800x450.jpg"));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<TourListGet> call = jsonPlaceHolderApi.getTourList(token);
        call.enqueue(new Callback<TourListGet>() {
            @Override
            public void onResponse(Call<TourListGet> call, Response<TourListGet> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

                    return;
                }

                TourListGet tours = response.body();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TourListGet> call, Throwable t) {
                Toast.makeText(Main2Activity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });


        tourAdapter = new TourAdapter(this, tourData);
        tourListView = findViewById(R.id.main2_tourListView);
        tourListView.setAdapter(tourAdapter);
        tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==position)
                {
                    Long ID= tourData.get(position).getId();
                    Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("Key_2", ID);
                    intent.putExtras(bundle);
                    bundle.putString("Key_1", token);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });
    }
    private void controlbutton (){

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                LoginManager.getInstance().logOut();

            }
        });
        creattour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,Main4Activity_Creat.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    private void Anhxa() {

        Backbutton = (Button)findViewById(R.id.backge);
        creattour = (Button)findViewById(R.id.addtour);
//

    }

}
