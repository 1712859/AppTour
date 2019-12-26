package com.example.tourviet;

import java.util.ArrayList;
import java.util.List;

class AddStopPointBody {
    long tourId;
    List<MyStopPoint> myStopPoints = new ArrayList<>();

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
        myStopPoint.avatar=avatar;

        myStopPoints.add(myStopPoint);
    }


    private static class MyStopPoint {
        String name;
        long arrivalAt;
        long leaveAt;
        double lat;
        double lng;
        long minCost;
        long maxCost;
        String avatar;
        int serviceTypeId = 1;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public List<MyStopPoint> getMyStopPoints() {
        return myStopPoints;
    }

    public void setMyStopPoints(List<MyStopPoint> myStopPoints) {
        this.myStopPoints = myStopPoints;
    }
}
