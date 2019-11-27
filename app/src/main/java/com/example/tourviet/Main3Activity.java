package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    Long ID;
    String token;
    TextView Id;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//..................................
        //......................................
        Anhxa();
        ButtonControl();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ID = bundle.getLong("Key_2");
            token = bundle.getString("Key_1");
        }

        String s = String.valueOf(ID);
        Id.setText(s);




    }

    private void ButtonControl() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        back = (Button)findViewById(R.id.backge_tourDetail);
        Id = findViewById(R.id.tourItem_title1);
    }
}
