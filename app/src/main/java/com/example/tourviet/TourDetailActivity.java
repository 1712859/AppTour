package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourDetailActivity extends AppCompatActivity {

    private static final int UPDATE_TOUR_REQUEST_CODE = 6666;
    TourInfo myTour = new TourInfo();
    StopPointAdapter stopPointAdapter;
    String token;
    long id;

    TextView tourId;
    TextView tourName;
    TextView minCost;
    TextView maxCost;
    TextView startDate;
    TextView endDate;
    TextView adult;
    TextView child;
    TextView status;
    ImageView tourImage;
    ListView stopPointList;

    Button addStopPointBtn;
    Button deleteTourBtn;
    Button updateTourBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        token = intent.getStringExtra("token");

        tourImage = findViewById(R.id.tourDetail_image);
        tourName = findViewById(R.id.tourDetail_tourName);
        tourId = findViewById(R.id.tourDetail_id);
        adult = findViewById(R.id.tourDetail_adult);
        child = findViewById(R.id.tourDetail_child);
        minCost = findViewById(R.id.tourDetail_minCost);
        maxCost = findViewById(R.id.tourDetail_maxCost);
        startDate = findViewById(R.id.tourDetail_startDate);
        endDate = findViewById(R.id.tourDetail_endDate);
        status = findViewById(R.id.tourDetail_status);
        stopPointList = findViewById(R.id.tourDetail_stopPointList);
        addStopPointBtn = findViewById(R.id.tourDetail_addStopPointBtn);
        deleteTourBtn = findViewById(R.id.tourDetail_deleteTourBtn);
        updateTourBtn = findViewById(R.id.tourDetail_updateTourBtn);

        updateTourInfo();

        stopPointAdapter = new StopPointAdapter(getApplicationContext(), myTour.getStopPoints());
        stopPointList.setAdapter(stopPointAdapter);

        updateStopPointList();

        addStopPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(getApplicationContext(), TourUpdateActivity.class);
                updateIntent.putExtra("tourId", myTour.getId());
                updateIntent.putExtra("token", token);
                startActivityForResult(updateIntent, UPDATE_TOUR_REQUEST_CODE);
            }
        });

        deleteTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateTourInfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourInfo> call = jsonPlaceHolderApi.InfoTour(id, token);
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                    return;
                }

                myTour = response.body();


                tourName.setText(myTour.getName());
                tourId.setText(String.valueOf(myTour.getId()));
                adult.setText(String.valueOf(myTour.getAdults()));
                child.setText(String.valueOf(myTour.getChilds()));
                minCost.setText(String.valueOf(myTour.getMinCost()));
                maxCost.setText(String.valueOf(myTour.getMaxCost()));
                startDate.setText(myTour.getStartDate());
                endDate.setText(myTour.getEndDate());
                status.setText(getResources().getStringArray(R.array.tourDetail_status)[myTour.getStatus()]);

            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void updateStopPointList() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == UPDATE_TOUR_REQUEST_CODE) {
                //myTour = (TourItem) data.getSerializableExtra("tourItem");

                updateTourInfo();
            }
        }
    }
}
