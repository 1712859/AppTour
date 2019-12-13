package com.example.tourviet;

import java.util.List;

class UserTour {

    private int totals;
    private List<TourItem> tours;

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public List<TourItem> getTours() {
        return tours;
    }

    public void setTours(List<TourItem> tours) {
        this.tours = tours;
    }
}
