package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class TourDetailActivity extends AppCompatActivity {

    private static final int UPDATE_TOUR_REQUEST_CODE = 6666;
    TourItem myTour;
    String token;

    TextView id;
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
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            myTour = (TourItem) bundle.getSerializable("item");
            token = bundle.getString("token");
        }

        tourImage = findViewById(R.id.tourDetail_image);
        tourName = findViewById(R.id.tourDetail_tourName);
        id = findViewById(R.id.tourDetail_id);
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
                Intent updateIntent = new Intent(TourDetailActivity.this, TourUpdateActivity.class);
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
        Picasso.get().load(myTour.getAvatar()).into(tourImage);
        tourName.setText(myTour.getTourName());
        id.setText(String.valueOf(myTour.getId()));
        adult.setText(String.valueOf(myTour.getAdults()));
        child.setText(String.valueOf(myTour.getChilds()));
        price.setText(String.valueOf(myTour.getMaxCost()));
        startDate.setText(myTour.getStartDate());
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
