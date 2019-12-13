package com.example.tourviet;

class TourCreateForm {
    private String name;
    private int startDate;
    private int endDate;
    private double sourceLat;
    private double sourceLong;
    private double desLat;
    private double desLong;
    private boolean isPrivate;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private String avatar;

    public TourCreateForm(String name, String startDate, String endDate, double sourceLat, double sourceLong, double desLat, double desLong, boolean isPrivate, int adults, int childs, int minCost, int maxCost, String avatar) {
        this.name = name;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.avatar = avatar;

        endDate = endDate.replaceAll("(\\\\|\\/|-)", "");
        this.endDate = Integer.valueOf(endDate);

        startDate = startDate.replaceAll("(\\\\|\\/|-)", "");
        this.startDate = Integer.valueOf(startDate);
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

    public void setStartDate(String startDate) {
        startDate = startDate.replaceAll("(\\\\|\\/|-)", "");
        this.startDate = Integer.valueOf(startDate);
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        endDate = endDate.replaceAll("(\\\\|\\/|-)", "");
        this.endDate = Integer.valueOf(endDate);
    }

    public double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public double getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(double sourceLong) {
        this.sourceLong = sourceLong;
    }

    public double getDesLat() {
        return desLat;
    }

    public void setDesLat(double desLat) {
        this.desLat = desLat;
    }

    public double getDesLong() {
        return desLong;
    }

    public void setDesLong(double desLong) {
        this.desLong = desLong;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
