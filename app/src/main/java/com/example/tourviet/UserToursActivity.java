package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    List<TourItem> tourData = new ArrayList<>();
    TourAdapter tourAdapter;
    ListView tourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__user_tour);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            token = bundle.getString("Key_1");
        }
        Button Back = findViewById(R.id.backge);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_UserTour.this, Main2Activity_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("Key_1", token);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        try {
            loaddata();

            tourAdapter = new TourAdapter(this, tourData);
            tourListView = findViewById(R.id.main2_tourListView);
            tourListView.setAdapter(tourAdapter);
            tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    long ID = tourData.get(position).getId();
                    Intent intent = new Intent(MainActivity_UserTour.this, TourDetailActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("item", tourData.get(position));
                    bundle.putString("token", token);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(MainActivity_UserTour.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
        }

    }

    private void loaddata() {
        tourData.add(new TourItem(9999, 0, "Đi hạ long (sample)", 100000, 200000, "21/10/1999", "27/2/2014", 0, 0, false, "https://tour.dulichvietnam.com.vn/uploads/tour/1554713265_tour-ha-long-3.jpg"));
        tourData.add(new TourItem(8888, 0, "Đi đà lạt (sample)", 700000, 900000, "14/5/216", "8/2/374", 0, 0, false, "https://cdn3.ivivu.com/2013/09/khu-nghi-duong-terracotta-da-lat-1-800x450.jpg"));
        User_tour user_tour = new User_tour(
                1,
                "10"
        );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourListGet> cal1 = jsonPlaceHolderApi.getTourUserList(token,user_tour);
        cal1.enqueue(new Callback<TourListGet>() {
            @Override
            public void onResponse(Call<TourListGet> call, Response<TourListGet> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity_UserTour.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
                    return;
                }

                TourListGet tours = response.body();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TourListGet> call, Throwable t) {
                Toast.makeText(MainActivity_UserTour.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
