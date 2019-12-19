package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tourviet.TourCreateActivity.PICK_STOP_POINT_REQUEST_CODE;

public class TourUpdateActivity extends AppCompatActivity {

    String token;
    long tourId;
    TourItem tour = new TourItem();

    String sourceName;
    String desName;

    EditText tourNameText;
    EditText startDateText;
    EditText endDateText;
    EditText adultsText;
    EditText childsText;
    EditText minCostText;
    EditText maxCostText;
    EditText avatarText;
    Spinner statusSpn;
    RadioButton isPrivateBtn;
    RadioButton isPublicBtn;
    Button pickStopPointBtn;
    Button confirmBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_update);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        tourId = intent.getLongExtra("tourId", -1);

        tourNameText = findViewById(R.id.tourUpdate_tourName);
        startDateText = findViewById(R.id.tourUpdate_startDate);
        endDateText = findViewById(R.id.tourUpdate_endDate);
        adultsText = findViewById(R.id.tourUpdate_adults);
        childsText = findViewById(R.id.tourUpdate_childs);
        minCostText = findViewById(R.id.tourUpdate_minCost);
        maxCostText = findViewById(R.id.tourUpdate_maxCost);
        avatarText = findViewById(R.id.tourUpdate_avatar);
        statusSpn = findViewById(R.id.tourUpdate_statusSpn);
        isPrivateBtn = findViewById(R.id.tourUpdate_privateBtn);
        isPublicBtn = findViewById(R.id.tourUpdate_publicBtn);
        pickStopPointBtn = findViewById(R.id.tourUpdate_pickStopPointBtn);
        confirmBtn = findViewById(R.id.tourUpdate_confirmBtn);

        getTour();

        ArrayAdapter<CharSequence> arrayAdapter =
                ArrayAdapter.createFromResource(this, R.array.tourUpdate_status, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpn.setAdapter(arrayAdapter);

        statusSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setStatus(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        isPrivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tour.setPrivate(true);
            }
        });

        isPublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tour.setPrivate(false);
            }
        });


        pickStopPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickStopPointActivity.class);
                intent.putExtra("sourceName", sourceName);
                intent.putExtra("sourceLat", tour.getSourceLat());
                intent.putExtra("sourceLong", tour.getSourceLong());
                intent.putExtra("desName", desName);
                intent.putExtra("desLat", tour.getDesLat());
                intent.putExtra("desLong", tour.getDesLong());
                startActivityForResult(intent, PICK_STOP_POINT_REQUEST_CODE);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startDateText.getText().toString().equals("") || endDateText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "xin hãy nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    updateTour();
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                Call<TourItem> call = jsonPlaceHolderApi.UpdateTour(tour, token);
                call.enqueue(new Callback<TourItem>() {
                    @Override
                    public void onResponse(Call<TourItem> call, Response<TourItem> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "lỗi dữ liệu", Toast.LENGTH_LONG).show();
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<TourItem> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "lỗi kết nối đến server", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_STOP_POINT_REQUEST_CODE) {

                sourceName = data.getStringExtra("sourceName");
                tour.setSourceLat(data.getDoubleExtra("sourceLat", 0));
                tour.setSourceLong(data.getDoubleExtra("sourceLong", 0));
                desName = data.getStringExtra("desName");
                tour.setDesLat(data.getDoubleExtra("desLat", 0));
                tour.setDesLong(data.getDoubleExtra("desLong", 0));

                updateDisplayString();
            }
        }
    }

    private void getTour() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi placeHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<TourItem> call = placeHolderApi.CloneTour(new TourIdHolder(tourId), token);
        call.enqueue(new Callback<TourItem>() {
            @Override
            public void onResponse(Call<TourItem> call, Response<TourItem> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi lấy thông tin json từ server", Toast.LENGTH_LONG).show();
                }

                tour = response.body();

                if (tour.isPrivate()) {
                    isPrivateBtn.toggle();
                } else {
                    isPublicBtn.toggle();
                }

                updateDisplayString();
            }

            @Override
            public void onFailure(Call<TourItem> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateTour() {
        tour.setTourName(tourNameText.getText().toString());
        tour.setStartDate(startDateText.getText().toString());
        tour.setEndDate(endDateText.getText().toString());
        tour.setAdults(Integer.valueOf(adultsText.getText().toString()));
        tour.setChilds(Integer.valueOf(childsText.getText().toString()));
        tour.setMaxCost(Integer.valueOf(maxCostText.getText().toString()));
        tour.setMinCost(Integer.valueOf(minCostText.getText().toString()));
        tour.setAvatar(avatarText.getText().toString());
    }

    private void updateDisplayString() {
        tourNameText.setText(tour.getTourName());
        startDateText.setText(tour.getStartDate());
        endDateText.setText(tour.getEndDate());
        adultsText.setText(String.valueOf(tour.getAdults()));
        childsText.setText(String.valueOf(tour.getChilds()));
        minCostText.setText(String.valueOf(tour.getMinCost()));
        maxCostText.setText(String.valueOf(tour.getMaxCost()));
        avatarText.setText(tour.getAvatar());
    }
}
