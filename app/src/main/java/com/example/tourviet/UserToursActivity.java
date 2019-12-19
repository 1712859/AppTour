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

public class UserToursActivity extends AppCompatActivity {

    String token;
    int currentPage;
    Button createTourBtn, nextPageBtn, previousPageBtn;
    TextView currentPageText;
    List<TourItem> tourData = new ArrayList<>();
    TourAdapter tourAdapter;
    ListView tourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tours);


        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        tourList = findViewById(R.id.userTour_list);
        createTourBtn = findViewById(R.id.userTour_createTourBtn);
        previousPageBtn = findViewById(R.id.userTour_previousPageBtn);
        nextPageBtn = findViewById(R.id.userTour_nextPageBtn);
        currentPageText = findViewById(R.id.userTour_currentPageText);

        tourAdapter = new TourAdapter(UserToursActivity.this, tourData);
        tourList.setAdapter(tourAdapter);
        tourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TourDetailActivity.class);
                intent.putExtra("id", tourData.get(position).getId());
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        currentPage = 1;
        getTourList(currentPage);


        createTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TourCreateActivity.class);
                intent.putExtra("token", token);
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


    }


    private void getTourList(int page) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<UserTour> cal1 = jsonPlaceHolderApi.getUserTourList(page, 10, token);
        cal1.enqueue(new Callback<UserTour>() {
            @Override
            public void onResponse(Call<UserTour> call, Response<UserTour> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                    return;
                }

                UserTour tours = response.body();
                tourData.clear();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UserTour> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
