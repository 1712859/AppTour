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

import static com.example.tourviet.TourCreateActivity.PICK_START_END_REQUEST_CODE;

public class TourUpdateActivity extends AppCompatActivity {

    String token;
    long id;
    TourInfo myTour = new TourInfo();

    String sourceName, desName;
    double sourceLat, desLat, sourceLong, desLong;

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
    Button pickStartEndBtn;
    Button confirmBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_update);

        try {
            setupVariable();
            findView();
            setupStatusSpn();
            getTour();

        } catch (Exception e) {
            e.printStackTrace();
        }


        isPrivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTour.setPrivate(true);
            }
        });

        isPublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTour.setPrivate(false);
            }
        });


        pickStartEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickStartEndActivity.class);
                intent.putExtra("sourceName", sourceName);
                intent.putExtra("sourceLat", sourceLat);
                intent.putExtra("sourceLong", sourceLong);
                intent.putExtra("desName", desName);
                intent.putExtra("desLat", desLat);
                intent.putExtra("desLong", desLong);
                startActivityForResult(intent, PICK_START_END_REQUEST_CODE);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDateText.getText().toString().equals("") || endDateText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "xin hãy nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }
                packInformation();
                updateTour();
            }
        });

    }

    private void setupVariable() {
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        id = intent.getLongExtra("tourId", -1);
    }

    private void findView() {
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
        pickStartEndBtn = findViewById(R.id.tourUpdate_pickStartEndBtn);
        confirmBtn = findViewById(R.id.tourUpdate_confirmBtn);
    }

    private void setupStatusSpn() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tourUpdate_status,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpn.setAdapter(arrayAdapter);

        statusSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myTour.setStatus(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_START_END_REQUEST_CODE) {

                sourceName = data.getStringExtra("sourceName");
                sourceLat = data.getDoubleExtra("sourceLat", 0);
                sourceLong = data.getDoubleExtra("sourceLong", 0);
                desName = data.getStringExtra("desName");
                desLat = data.getDoubleExtra("desLat", 0);
                desLong = data.getDoubleExtra("desLong", 0);

                updateDisplayString();
            }
        }
    }

    private void getTour() {

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

                if (myTour.isPrivate()) {
                    isPrivateBtn.toggle();
                } else {
                    isPublicBtn.toggle();
                }
                statusSpn.setSelection(myTour.getStatus() + 1);

                updateDisplayString();
            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateTour() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        TourUpdate tourUpdate = new TourUpdate(myTour, sourceLat, sourceLong, desLat, desLong);
        Call<TourUpdate> call = jsonPlaceHolderApi.UpdateTour(tourUpdate, token);
        call.enqueue(new Callback<TourUpdate>() {
            @Override
            public void onResponse(Call<TourUpdate> call, Response<TourUpdate> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TourUpdate> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void packInformation() {
        myTour.setName(tourNameText.getText().toString());
        myTour.setStartDate(startDateText.getText().toString());
        myTour.setEndDate(endDateText.getText().toString());
        myTour.setAdults(Integer.valueOf(adultsText.getText().toString()));
        myTour.setChilds(Integer.valueOf(childsText.getText().toString()));
        myTour.setMaxCost(maxCostText.getText().toString());
        myTour.setMinCost(minCostText.getText().toString());
        myTour.setStatus(statusSpn.getSelectedItemPosition() - 1);
    }

    private void updateDisplayString() {
        tourNameText.setText(myTour.getName());
        startDateText.setText(myTour.getStartDate());
        endDateText.setText(myTour.getEndDate());
        adultsText.setText(String.valueOf(myTour.getAdults()));
        childsText.setText(String.valueOf(myTour.getChilds()));
        minCostText.setText(String.valueOf(myTour.getMinCost()));
        maxCostText.setText(String.valueOf(myTour.getMaxCost()));
        statusSpn.setSelection(myTour.getStatus() + 1);
    }
}
