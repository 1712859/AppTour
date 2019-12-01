package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.Year;
import java.util.Calendar;

public class Main4Activity_Creat extends AppCompatActivity {
    Button back,creat;
    EditText TourName, DateStart,DateEnd,Adults,Childs,MinCost,MaxCost,LinkImage;
    String token, image_url;
    int Daystart,monthstart,yearstart;
    int Dayend,monthend,yearend;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4__creat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            token = bundle.getString("Key_1");
            image_url = bundle.getString("Key_3");
        }
        Anhxa();
        ControlButton();
    }

    private void ControlButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main4Activity_Creat.this,Main2Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                bundle1.putString("Key_3", image_url);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main4Activity_Creat.this,Main5Activity_stopPoint.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                bundle1.putString("Key_3", image_url);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        DateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                yearstart = calendar.get(Calendar.YEAR);
                monthstart = calendar.get(Calendar.MONTH);
                Daystart = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Main4Activity_Creat.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateStart.setText(day+"/"+month+"/"+year);
                    }

                }, yearstart, monthstart, Daystart);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        DateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                yearend = calendar.get(Calendar.YEAR);
                monthend = calendar.get(Calendar.MONTH);
                Dayend = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Main4Activity_Creat.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateEnd.setText(day+"/"+month+"/"+year);
                    }

                }, yearend, monthend, Dayend);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }

    private void Anhxa() {
        back = (Button)findViewById(R.id.backgeCreat);
        creat = (Button)findViewById(R.id.CreateTour);
        TourName = (EditText)findViewById(R.id.TourName);
        DateStart = (EditText)findViewById(R.id.DayStart);
        DateEnd = (EditText)findViewById(R.id.DayEnd);
        Adults = (EditText)findViewById(R.id.edittextNguoiLon);
        Childs = (EditText)findViewById(R.id.edittextTreEm);
        MinCost = (EditText)findViewById(R.id.edittextMinCost);
        MaxCost = (EditText)findViewById(R.id.edittextMaxCost);
        LinkImage = (EditText)findViewById(R.id.edittextImageTour);

    }
}
