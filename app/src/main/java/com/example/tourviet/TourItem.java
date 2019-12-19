package com.example.tourviet;

import java.io.Serializable;

public class TourItem implements Serializable {

    private long id;
    private long hostID;
    private String name;
    private String startDate;
    private String endDate;
    private double sourceLat;
    private double sourceLong;
    private double desLat;
    private double desLong;
    private int adults;
    private int childs;
    private long minCost;
    private long maxCost;
    private String avatar;
    private boolean isPrivate;
    private int status;
    private boolean isHost;
    private boolean isKicked;

    public TourItem(long id,
                    int status,
                    String name,
                    long minCost,
                    long maxCost,
                    String startDate,
                    String endDate,
                    int adults,
                    int childs,
                    boolean isPrivate,
                    String avatar) {
        endDate = endDate.replaceAll("[^0-9]", "");
        startDate = startDate.replaceAll("[^0-9]", "");

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

    public TourItem(long id,
                    long hostID,
                    int status,
                    String name,
                    long minCost,
                    long maxCost,
                    String startDate,
                    String endDate,
                    int adults,
                    int childs,
                    boolean isPrivate,
                    String avatar,
                    boolean isHost,
                    boolean isKicked) {
        endDate = endDate.replaceAll("[^0-9]", "");
        startDate = startDate.replaceAll("[^0-9]", "");

        this.id = id;
        this.hostID = hostID;
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
        this.isHost = isHost;
        this.isKicked = isKicked;
    }

    public TourItem() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHostID() {
        return hostID;
    }

    public void setHostID(long hostID) {
        this.hostID = hostID;
    }

    public String getTourName() {
        return name;
    }

    public void setTourName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        startDate = startDate.replaceAll("[^0-9]", "");
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        endDate = endDate.replaceAll("[^0-9]", "");
        this.endDate = endDate;
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

    public long getMinCost() {
        return minCost;
    }

    public void setMinCost(long minCost) {
        this.minCost = minCost;
    }

    public long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(long maxCost) {
        this.maxCost = maxCost;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isKicked() {
        return isKicked;
    }

    public void setKicked(boolean kicked) {
        isKicked = kicked;
    }
}
