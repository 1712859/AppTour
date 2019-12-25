package com.example.tourviet;

class TourUpdate {

    private String id;
    private String name;
    private int startDate;
    private int endDate;
    private double sourceLat;
    private double sourceLong;
    private double desLat;
    private double desLong;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private boolean isPrivate;
    private int status;

    public TourUpdate(TourInfo tourInfo, double sourceLat, double sourceLong, double desLat, double desLong) {
        this.id = String.valueOf(tourInfo.getId());
        this.name = tourInfo.getName();
        this.startDate = Integer.valueOf(tourInfo.getStartDate());
        this.endDate = Integer.valueOf(tourInfo.getEndDate());
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.adults = tourInfo.getAdults();
        this.childs = tourInfo.getChilds();
        this.minCost = Integer.valueOf(tourInfo.getMinCost());
        this.maxCost = Integer.valueOf(tourInfo.getMaxCost());
        this.isPrivate = tourInfo.isPrivate();
        this.status = tourInfo.getStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(int sourceLat) {
        this.sourceLat = sourceLat;
    }

    public double getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(int sourceLong) {
        this.sourceLong = sourceLong;
    }

    public double getDesLat() {
        return desLat;
    }

    public void setDesLat(int desLat) {
        this.desLat = desLat;
    }

    public double getDesLong() {
        return desLong;
    }

    public void setDesLong(int desLong) {
        this.desLong = desLong;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChilds() {
        return childs;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
