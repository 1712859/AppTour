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
    TourItem myTour = new TourItem();
    String token;
    long id;

    TextView tourId;
    TextView tourName;
    TextView price;
    TextView startDate;
    TextView adult;
    TextView child;
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
        price = findViewById(R.id.tourDetail_price);
        startDate = findViewById(R.id.tourDetail_startDate);
        stopPointList = findViewById(R.id.tourDetail_stopPointList);
        addStopPointBtn = findViewById(R.id.tourDetail_addStopPointBtn);
        deleteTourBtn = findViewById(R.id.tourDetail_deleteTourBtn);
        updateTourBtn = findViewById(R.id.tourDetail_updateTourBtn);

        updateTourInfo();
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
        Call<TourItem> call = jsonPlaceHolderApi.CloneTour(new TourIdHolder(id), token);
        call.enqueue(new Callback<TourItem>() {
            @Override
            public void onResponse(Call<TourItem> call, Response<TourItem> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                    return;
                }

                myTour = response.body();

                Picasso.get().load(myTour.getAvatar()).into(tourImage);
                tourName.setText(myTour.getTourName());
                tourId.setText(String.valueOf(myTour.getId()));
                adult.setText(String.valueOf(myTour.getAdults()));
                child.setText(String.valueOf(myTour.getChilds()));
                price.setText(String.valueOf(myTour.getMaxCost()));
                startDate.setText(myTour.getStartDate());

            }

            @Override
            public void onFailure(Call<TourItem> call, Throwable t) {
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
                myTour = (TourItem) data.getSerializableExtra("tourItem");

                updateTourInfo();
            }
        }
    }
}
