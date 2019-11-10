package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TourAdapter tourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        List<TourItem> sampleTourData = new ArrayList<TourItem>();

        // tạo dữ liệu giả, phần lấy dữ liệu từ api sẽ làm sau
        sampleTourData.add(new TourItem(0, 0, "Đi hạ long", 100000, 200000, "21/10/1999", "27/2/2014", 0,0,false, "https://tour.dulichvietnam.com.vn/uploads/tour/1554713265_tour-ha-long-3.jpg"));
        sampleTourData.add(new TourItem(0, 0, "Đi đà lạc", 700000, 900000, "14/5/216", "8/2/374", 0,0,false, "https://cdn3.ivivu.com/2013/09/khu-nghi-duong-terracotta-da-lat-1-800x450.jpg"));

        // vì một lý do nào đó mà phần avatar ko hiển thị dc trên máy ảo nhưng vẫn hiển thị bình thường trên máy thật
        tourAdapter=new TourAdapter(this, sampleTourData);
        ListView tourListView= findViewById(R.id.main2_tourListView);
        tourListView.setAdapter(tourAdapter);
    }
}
