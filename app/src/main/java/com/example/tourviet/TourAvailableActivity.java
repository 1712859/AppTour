package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourAvailableActivity extends AppCompatActivity {
    String token;
    int currentPage;
    ListView tourListView;
    TourAdapter tourAdapter;
    Button createTourBtn, previousPageBtn, nextPageBtn;
    TextView currentPageText;
    List<TourItem> tourData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_available);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        currentPage = 1;
        try {
            tourListView = findViewById(R.id.tourAvailable_tourListView);
            createTourBtn = findViewById(R.id.tourAvailable_createTourBtn);
            previousPageBtn = findViewById(R.id.tourAvailable_previousPageBtn);
            nextPageBtn = findViewById(R.id.tourAvailable_nextPageBtn);
            currentPageText = findViewById(R.id.tourAvailable_currentPageText);


            setupListview();
            //loaddata();
            getTourList(currentPage);

            createTourBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TourAvailableActivity.this, TourCreateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("token", token);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            nextPageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPage++;
                    getTourList(currentPage);
                    currentPageText.setText(String.valueOf(currentPage));
                }
            });

            previousPageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPage <= 1)
                        return;

                    currentPage--;
                    getTourList(currentPage);
                    currentPageText.setText(String.valueOf(currentPage));
                }
            });

        } catch (Exception e) {
            Toast.makeText(TourAvailableActivity.this, "lỗi khởi tạo màn hình.", Toast.LENGTH_LONG).show();
        }
    }


    private void setupListview() {
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

    private void loaddata() {
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
                    Toast.makeText(TourAvailableActivity.this, "lỗi lấy thông tin json từ server.", Toast.LENGTH_LONG).show();

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
                Toast.makeText(TourAvailableActivity.this, "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getTourList(int page) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourListGet> cal1 = jsonPlaceHolderApi.getTourList(page, 10, token);
        cal1.enqueue(new Callback<TourListGet>() {
            @Override
            public void onResponse(Call<TourListGet> call, Response<TourListGet> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TourAvailableActivity.this, "lỗi lấy thông tin json từ server.", Toast.LENGTH_LONG).show();
                    return;
                }

                TourListGet tours = response.body();
                tourData.clear();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TourListGet> call, Throwable t) {
                Toast.makeText(TourAvailableActivity.this, "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
