package com.example.tourviet;

import android.os.Bundle;
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

public class Main2Activity extends AppCompatActivity {

    TourAdapter tourAdapter;
    ListView tourListView;
    List<TourItem> tourData = new ArrayList<TourItem>();

    String temporaryLoginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIzNjkiLCJwaG9uZSI6IjA4NTg0NTYxNTIiLCJlbWFpbCI6InR1YmF0bzE5OTlAZ21haWwuY29tIiwiZXhwIjoxNTc2MjIyNDY0MTg1LCJhY2NvdW50IjoidXNlciIsImlhdCI6MTU3MzYzMDQ2NH0.0CruSddOgakdzQdG98VkPpFBSTNOq2h9FZq6r6vvIQs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tourData.add(new TourItem(0, 0, "Đi hạ long (sample)", 100000, 200000, "21/10/1999", "27/2/2014", 0, 0, false, "https://tour.dulichvietnam.com.vn/uploads/tour/1554713265_tour-ha-long-3.jpg"));
        tourData.add(new TourItem(0, 0, "Đi đà lạc (sample)", 700000, 900000, "14/5/216", "8/2/374", 0, 0, false, "https://cdn3.ivivu.com/2013/09/khu-nghi-duong-terracotta-da-lat-1-800x450.jpg"));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<TourListGet> call = jsonPlaceHolderApi.getTourList(temporaryLoginToken);
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
    }
}
