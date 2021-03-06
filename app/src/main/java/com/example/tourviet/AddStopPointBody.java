package com.example.tourviet;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class AddStopPointBody {
    private long tourId;
    private List<MyStopPoint> stopPoints = new ArrayList<>();

    public AddStopPointBody() {

    }

    public AddStopPointBody(
            long tourId,
            String name,
            long arrivalAt,
            long leaveAt,
            double lat,
            double lng,
            long minCost,
            long maxCost,
            String avatar) {
        this.tourId = tourId;

        MyStopPoint myStopPoint = new MyStopPoint();
        myStopPoint.name = name;
        myStopPoint.arrivalAt = arrivalAt;
        myStopPoint.leaveAt = leaveAt;
        myStopPoint.lat = lat;
        myStopPoint.lng = lng;
        myStopPoint.minCost = minCost;
        myStopPoint.maxCost = maxCost;
        //myStopPoint.avatar=avatar;

        stopPoints.add(myStopPoint);
    }


    private static class MyStopPoint {
        public String name;
        public long arrivalAt;
        public long leaveAt;
        public double lat;
        @SerializedName("long")
        public double lng;
        public long minCost;
        public long maxCost;
        //public String avatar;
        public int serviceTypeId = 1;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public List<MyStopPoint> getMyStopPoints() {
        return stopPoints;
    }

    public void setMyStopPoints(List<MyStopPoint> myStopPoints) {
        this.stopPoints = myStopPoints;
    }
}
