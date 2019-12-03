package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class TourDetailActivity extends AppCompatActivity {

    TourItem myTour;
    String token;

    ImageView tourImage;
    TextView tourName;
    TextView id;
    TextView adult;
    TextView child;
    TextView price;
    TextView startDate;
    ListView stopPoints;


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
        stopPoints = findViewById(R.id.tourDetail_stopPointList);

        Picasso.get().load(myTour.getImageUrl()).into(tourImage);
        tourName.setText(myTour.getName());
        id.setText(String.valueOf(myTour.getId()));
        adult.setText(String.valueOf(myTour.getAdults()));
        child.setText(String.valueOf(myTour.getChilds()));
        price.setText(String.valueOf(myTour.getMaxCost()));
        startDate.setText(myTour.getStartDate());

    }

}
