package com.example.tourviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PickStopPointActivity extends AppCompatActivity {

    static int GET_START_POINT_REQUEST_CODE = 2;
    static int GET_END_POINT_REQUEST_CODE = 3;
    double sourceLas, sourceLong, desLas, desLong;
    String sourceName, desName;
    Button pickStartPointBtn, pickEndPointBtn, confirmBtn;
    TextView startPointInfo, endPointInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_stop_point);

        Intent intent = getIntent();
        sourceName = intent.getStringExtra("sourceName");
        sourceLas = intent.getDoubleExtra("sourceLas", 0);
        sourceLong = intent.getDoubleExtra("sourceLong", 0);
        desName = intent.getStringExtra("desName");
        desLas = intent.getDoubleExtra("desLas", 0);
        desLong = intent.getDoubleExtra("desLong", 0);

        pickStartPointBtn = findViewById(R.id.pickStopPoint_pickStartPointBtn);
        pickEndPointBtn = findViewById(R.id.pickStopPoint_pickEndPointBtn);
        confirmBtn = findViewById(R.id.pickStopPoint_confirmBtn);
        startPointInfo = findViewById(R.id.pickStopPoint_startPointInfo);
        endPointInfo = findViewById(R.id.pickStopPoint_endPointInfo);


        String infoText = "Vị trí bắt đầu: " + sourceName + '\n' + sourceLas + ", " + sourceLong;
        startPointInfo.setText(infoText);
        infoText = "Vị trí kết thúc: " + sourceName + '\n' + sourceLas + ", " + sourceLong;
        endPointInfo.setText(infoText);

        pickStartPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickStopPointActivity.this, MapGetLocationActivity.class);
                startActivityForResult(intent, GET_START_POINT_REQUEST_CODE);
            }
        });

        pickEndPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickStopPointActivity.this, MapGetLocationActivity.class);
                startActivityForResult(intent, GET_END_POINT_REQUEST_CODE);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("sourceName", sourceName);
        intent.putExtra("sourceLas", sourceLas);
        intent.putExtra("sourceLong", sourceLong);
        intent.putExtra("desName", desName);
        intent.putExtra("desLas", desLas);
        intent.putExtra("desLong", desLong);
        this.setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_START_POINT_REQUEST_CODE) {
                sourceName = data.getStringExtra("name");
                sourceLas = data.getDoubleExtra("las", 0);
                sourceLong = data.getDoubleExtra("long", 0);
                String infoText = "Vị trí bắt đầu: " + sourceName + '\n' + sourceLas + ", " + sourceLong;
                startPointInfo.setText(infoText);
            }
            if (requestCode == GET_END_POINT_REQUEST_CODE) {
                desName = data.getStringExtra("name");
                desLas = data.getDoubleExtra("las", 0);
                desLong = data.getDoubleExtra("long", 0);
                String infoText = "Vị trí kết thúc: " + sourceName + '\n' + sourceLas + ", " + sourceLong;
                endPointInfo.setText(infoText);
            }
        }
    }
}
