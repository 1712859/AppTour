package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AvailableTourActivity extends AppCompatActivity {
    String token, image_url;
    ListView tourListView;
    TourAdapter tourAdapter;
    List<TourItem> tourData = new ArrayList<TourItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_tour);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            token = bundle.getString("Key_1");
            image_url = bundle.getString("Key_3");
        }
        try {
            loaddata();
        } catch (Exception e) {
            Toast.makeText(AvailableTourActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
        }
    }

    private void loaddata() {
        tourData.add(new TourItem(99, 0, "Đi hạ long (sample)", 100000, 200000, "21/10/1999", "27/2/2014", 0, 0, false, "https://tour.dulichvietnam.com.vn/uploads/tour/1554713265_tour-ha-long-3.jpg"));
        tourData.add(new TourItem(66, 0, "Đi đà lạc (sample)", 700000, 900000, "14/5/216", "8/2/374", 0, 0, false, "https://cdn3.ivivu.com/2013/09/khu-nghi-duong-terracotta-da-lat-1-800x450.jpg"));

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
                    Toast.makeText(AvailableTourActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

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
            }

            @Override
            public void onFailure(retrofit2.Call<User_infor> call, Throwable t) {
                Toast.makeText(AvailableTourActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourListGet> call1 = jsonPlaceHolderApi.getTourList(token);
        call1.enqueue(new Callback<TourListGet>() {
            @Override
            public void onResponse(Call<TourListGet> call, Response<TourListGet> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AvailableTourActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

                    return;
                }

                TourListGet tours = response.body();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TourListGet> call, Throwable t) {
                Toast.makeText(AvailableTourActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });


        tourAdapter = new TourAdapter(this, tourData);
        tourListView = findViewById(R.id.main2_tourListView);
        tourListView.setAdapter(tourAdapter);
        tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long ID = tourData.get(position).getId();
                Intent intent = new Intent(AvailableTourActivity.this, Main3Activity.class);
                Bundle bundle = new Bundle();

                bundle.putLong("Key_2", ID);
                //intent.putExtras(bundle);
                bundle.putString("Key_1", token);
                //intent.putExtras(bundle);
                bundle.putString("Key_3", image_url);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
