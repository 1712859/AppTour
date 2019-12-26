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
    Button createTourBtn, previousPageBtn, nextPageBtn;
    TextView currentPageText;
    List<TourItem> tourData = new ArrayList<>();
    TourAdapter tourAdapter;
    ListView tourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_available);

        try {
            setupVariable();
            findView();
            setupListview();

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

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "lỗi khởi tạo màn hình.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTourList(currentPage);
    }

    private void setupVariable() {
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        currentPage = 1;
    }

    private void findView() {
        tourListView = findViewById(R.id.tourAvailable_tourListView);
        createTourBtn = findViewById(R.id.tourAvailable_createTourBtn);
        previousPageBtn = findViewById(R.id.tourAvailable_previousPageBtn);
        nextPageBtn = findViewById(R.id.tourAvailable_nextPageBtn);
        currentPageText = findViewById(R.id.tourAvailable_currentPageText);
    }

    private void setupListview() {
        tourAdapter = new TourAdapter(this, tourData);
        tourListView.setAdapter(tourAdapter);
        tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TourDetailActivity.class);
                intent.putExtra("id", tourData.get(position).getId());
                intent.putExtra("token", token);
                startActivity(intent);
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
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                    return;
                }

                TourListGet tours = response.body();
                tourData.clear();
                tourData.addAll(tours.getTours());
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TourListGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
