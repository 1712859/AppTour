package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

public class TourDetailActivity extends AppCompatActivity {

    private static final int UPDATE_TOUR_REQUEST_CODE = 6666;
    String token;
    long id;

    TourInfo myTour = new TourInfo();
    StopPointAdapter stopPointAdapter;
    CommentAdapter commentAdapter;
    MemberAdapter memberAdapter;
    List<BaseAdapter> adapters = new ArrayList<>();
    List<StopPoint> myStopPoint = new ArrayList<>();
    List<TourComment> myComments = new ArrayList<>();
    List<TourMember> myMembers = new ArrayList<>();

    TextView tourId, tourName, minCost, maxCost, startDate, endDate, adult, child, status;
    ImageView tourImage;
    Spinner listSpinner;
    ListView infoList;
    Button addStopPointBtn, addCommentBtn, updateTourBtn, addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        try {
            setupVariable();
            findView();
            setupListSpinner();
            setupAdapter();

            listSpinner.setSelection(0);

            addStopPointBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddStopPointActivity.class);
                    intent.putExtra("id", myTour.getId());
                    intent.putExtra("token", token);
                    startActivity(intent);
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

            addCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TourDetailActivity.this, AddCommentActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("id", myTour.getId());
                    startActivity(intent);
                }
            });
            addUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(TourDetailActivity.this, InviteToTour.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Key_1", token);
                    intent.putExtras(bundle1);
                    bundle1.putString("Key_2", String.valueOf(id));
                    intent.putExtras(bundle1);
                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupVariable() {
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        token = intent.getStringExtra("token");
    }

    private void findView() {
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
        listSpinner = findViewById(R.id.tourDetail_listSpinner);
        infoList = findViewById(R.id.tourDetail_infoList);
        addStopPointBtn = findViewById(R.id.tourDetail_addStopPointBtn);
        addCommentBtn = findViewById(R.id.tourDetail_addCommentBtn);
        updateTourBtn = findViewById(R.id.tourDetail_updateTourBtn);
        addUser = findViewById(R.id.tourDetail_addUser);
    }

    private void setupListSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tourDetail_infoSpinner,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listSpinner.setAdapter(arrayAdapter);

        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    infoList.setAdapter(adapters.get((position)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupAdapter() {
        stopPointAdapter = new StopPointAdapter(TourDetailActivity.this, myStopPoint);
        commentAdapter = new CommentAdapter(TourDetailActivity.this, myComments);
        memberAdapter = new MemberAdapter(TourDetailActivity.this, myMembers);

        adapters.add(stopPointAdapter);
        adapters.add(commentAdapter);
        adapters.add(memberAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateTourInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                updateDisplay();
            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "lỗi kết nối đến server.", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void updateDisplay() {
        tourName.setText(myTour.getName());
        tourId.setText(String.valueOf(myTour.getId()));
        adult.setText(String.valueOf(myTour.getAdults()));
        child.setText(String.valueOf(myTour.getChilds()));
        minCost.setText(String.valueOf(myTour.getMinCost()));
        maxCost.setText(String.valueOf(myTour.getMaxCost()));
        startDate.setText(myTour.getStartDate());
        endDate.setText(myTour.getEndDate());
        status.setText(getResources().getStringArray(R.array.tourDetail_status)[myTour.getStatus() + 1]);

        myStopPoint.clear();
        myStopPoint.addAll(myTour.getStopPoints());
        stopPointAdapter.notifyDataSetChanged();

        myComments.clear();
        myComments.addAll(myTour.getComments());
        commentAdapter.notifyDataSetChanged();

        myMembers.clear();
        myMembers.addAll(myTour.getMembers());
        memberAdapter.notifyDataSetChanged();
    }

}
