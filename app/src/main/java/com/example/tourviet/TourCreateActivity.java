package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourCreateActivity extends AppCompatActivity {
    static final int PICK_STOP_POINT_REQUEST_CODE = 1;
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

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        //token tạm
        //token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIzNjkiLCJwaG9uZSI6IjA4NTg0NTYxNTIiLCJlbWFpbCI6InR1YmF0bzE5OTlAZ21haWwuY29tIiwiZXhwIjoxNTc2MjIyNDY0MTg1LCJhY2NvdW50IjoidXNlciIsImlhdCI6MTU3MzYzMDQ2NH0.0CruSddOgakdzQdG98VkPpFBSTNOq2h9FZq6r6vvIQs";


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

        privateBtn.toggle();

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
                if (allFormRequireFilled()) {
                    submitForm();
                } else {
                    Toast.makeText(TourCreateActivity.this, "Xin hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
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

    private boolean allFormRequireFilled() {
        if (tourName.getText().toString().equals("")) {
            return false;
        } else if (dateStart.getText().toString().equals("")) {
            return false;
        } else if (dateEnd.getText().toString().equals("")) {
            return false;
        } else if (sourceLas == 0) {
            return false;
        } else if (sourceLong == 0) {
            return false;
        } else if (desLas == 0) {
            return false;
        } else if (desLong == 0) {
            return false;
        }

        return true;
    }

    private void submitForm() {
        TourCreateForm form = new TourCreateForm(
                tourName.getText().toString(),
                dateStart.getText().toString(),
                dateEnd.getText().toString(),
                sourceLas,
                sourceLong,
                desLas,
                desLong,
                isPrivate,
                Integer.valueOf(adult.getText().toString()),
                Integer.valueOf(child.getText().toString()),
                Integer.valueOf(minCost.getText().toString()),
                Integer.valueOf(maxCost.getText().toString()),
                linkImage.getText().toString()
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourItem> call = jsonPlaceHolderApi.postTourCreateForm(form, token);
        call.enqueue(new Callback<TourItem>() {
            @Override
            public void onResponse(Call<TourItem> call, Response<TourItem> response) {
                TourItem result = response.body();
                finish();
            }

            @Override
            public void onFailure(Call<TourItem> call, Throwable t) {
                Toast.makeText(TourCreateActivity.this, "lỗi gửi thông tin lên server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
