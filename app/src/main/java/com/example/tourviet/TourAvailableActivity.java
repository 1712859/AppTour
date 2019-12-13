package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourAvailableActivity extends AppCompatActivity {
    String token;

    List<TourItem> tourData = new ArrayList<>();
    TourAdapter tourAdapter;

    ListView tourListView;
    Button createTourBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_available);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        try {
            setupListView();
            //getUserInfo();
            getUserTour();

            createTourBtn = findViewById(R.id.tourAvailable_createTourBtn);
            createTourBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TourAvailableActivity.this, TourCreateActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(TourAvailableActivity.this, "lỗi trong quá trình thiết lập màn hình.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupListView() {

        tourListView = findViewById(R.id.tourAvailable_tourListView);
        tourAdapter = new TourAdapter(this, tourData);
        tourListView.setAdapter(tourAdapter);


        tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long ID = tourData.get(position).getId();
                Intent intent = new Intent(TourAvailableActivity.this, TourDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("item", tourData.get(position));
                bundle.putString("token", token);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    private void getUserInfo() {
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
                    Toast.makeText(TourAvailableActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();

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
                Toast.makeText(TourAvailableActivity.this, "lỗi lấy thông tin từ server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserTour() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<UserTour> cal1 = jsonPlaceHolderApi.getUserTourList(1, 100, token);
        cal1.enqueue(new Callback<UserTour>() {
            @Override
            public void onResponse(Call<UserTour> call, Response<UserTour> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TourAvailableActivity.this, "lỗi lấy json từ server.", Toast.LENGTH_LONG).show();
                    return;
                }

                UserTour tours = response.body();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UserTour> call, Throwable t) {
                Toast.makeText(TourAvailableActivity.this, "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
