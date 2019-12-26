package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStopPointActivity extends AppCompatActivity {
    private static final int GET_LOCATION_REQUEST_CODE = 7777;
    String token;
    long id;

    AddStopPointBody addStopPointBody;

    EditText name, arrivalAt, leaveAt, minCost, maxCost, avatar;
    Button selectLocationBtn, confirmBtn;
    TextView selectedLocationText;

    String locationName;
    double locationLas, locationLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_point);

        try {
            setupVariable();
            findView();

            selectLocationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MapGetLocationActivity.class);
                    startActivityForResult(intent, GET_LOCATION_REQUEST_CODE);
                }
            });

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataIsOk()) {
                        packInformation();
                        addStopPoint();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean dataIsOk() {
        if (!name.getText().toString().equals("") ||
                !arrivalAt.getText().toString().equals("") ||
                !leaveAt.getText().toString().equals("") ||
                !minCost.getText().toString().equals("") ||
                !maxCost.getText().toString().equals(""))
            return true;
        return false;
    }

    private void packInformation() {
        addStopPointBody = new AddStopPointBody(
                id,
                name.getText().toString(),
                Long.valueOf(arrivalAt.getText().toString()),
                Long.valueOf(leaveAt.getText().toString()),
                locationLas,
                locationLong,
                Long.valueOf(minCost.getText().toString()),
                Long.valueOf(maxCost.getText().toString()),
                avatar.getText().toString()
        );
    }

    private void addStopPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Object> call = jsonPlaceHolderApi.addStopPoint(addStopPointBody, token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "lỗi dữ liệu.", Toast.LENGTH_LONG).show();
                    return;
                }
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setupVariable() {
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        id = intent.getLongExtra("id", -1);
    }

    private void findView() {
        name = findViewById(R.id.addStopPoint_name);
        arrivalAt = findViewById(R.id.addStopPoint_arrivalAt);
        leaveAt = findViewById(R.id.addStopPoint_leaveAt);
        minCost = findViewById(R.id.addStopPoint_minCost);
        maxCost = findViewById(R.id.addStopPoint_maxCost);
        avatar = findViewById(R.id.addStopPoint_avatar);
        selectLocationBtn = findViewById(R.id.addStopPoint_selectLocationBtn);
        confirmBtn = findViewById(R.id.addStopPoint_confirmBtn);
        selectedLocationText = findViewById(R.id.addStopPoint_address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GET_LOCATION_REQUEST_CODE) {
                locationName = data.getStringExtra("name");
                locationLas = data.getDoubleExtra("las", 0);
                locationLong = data.getDoubleExtra("long", 0);

                selectedLocationText.setText(locationName);
            }
        }
    }
}
