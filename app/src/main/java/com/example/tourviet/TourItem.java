package com.example.tourviet;

public class TourItem {
    private long id;
    private int status;
    private String name;
    private long minCost;
    private long maxCost;
    private String startDate;
    private String endDate;
    private int adults;
    private int childs;
    private boolean isPrivate;
    private String avatar;

    public TourItem() {

    }

    public TourItem(long id, int status, String name, long minCost, long maxCost, String startDate, String endDate, int adults, int childs, boolean isPrivate, String avatar) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public long getMinCost() {
        return minCost;
    }

    public long getMaxCost() {
        return maxCost;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChilds() {
        return childs;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinCost(long minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(long maxCost) {
        this.maxCost = maxCost;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}