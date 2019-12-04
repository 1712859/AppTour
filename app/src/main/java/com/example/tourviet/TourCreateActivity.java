package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TourCreateActivity extends AppCompatActivity {
    static int PICK_STOP_POINT_REQUEST_CODE = 1;
    Button createBtn, pickStopPointBtn;
    RadioButton privateBtn, publicBtn;
    EditText tourName, dateStart, dateEnd, adult, child, minCost, maxCost, linkImage;
    boolean isPrivate;
    double sourceLas, sourceLong, desLas, desLong;
    String sourceName, desName;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_create);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            token = bundle.getString("token");
        }

        createBtn = findViewById(R.id.createTour_createBtn);
        pickStopPointBtn = findViewById(R.id.createTour_pickStopPoint);
        privateBtn = findViewById(R.id.createTour_privateBtn);
        publicBtn = findViewById(R.id.createTour_publicBtn);
        tourName = findViewById(R.id.createTour_tourName);
        dateStart = findViewById(R.id.createTour_dateStart);
        dateEnd = findViewById(R.id.createTour_dateEnd);
        adult = findViewById(R.id.createTour_adult);
        child = findViewById(R.id.createTour_child);
        minCost = findViewById(R.id.createTour_minCost);
        maxCost = findViewById(R.id.createTour_maxCost);
        linkImage = findViewById(R.id.createTour_imageUrl);


        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrivate = true;
            }
        });

        publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrivate = false;
            }
        });

        pickStopPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourCreateActivity.this, PickStopPointActivity.class);
                intent.putExtra("sourceName", sourceName);
                intent.putExtra("sourceLas", sourceLas);
                intent.putExtra("sourceLong", sourceLong);
                intent.putExtra("desName", desName);
                intent.putExtra("desLas", desLas);
                intent.putExtra("desLong", desLong);
                startActivityForResult(intent, PICK_STOP_POINT_REQUEST_CODE);
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_STOP_POINT_REQUEST_CODE) {
                sourceName = data.getStringExtra("sourceName");
                sourceLas = data.getDoubleExtra("sourceLas", 0);
                sourceLong = data.getDoubleExtra("sourceLong", 0);
                desName = data.getStringExtra("desName");
                desLas = data.getDoubleExtra("desLas", 0);
                desLong = data.getDoubleExtra("desLong", 0);
            }
        }
    }
}
