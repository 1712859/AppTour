package com.example.tourviet;

import java.io.Serializable;

public class TourItem implements Serializable {
    private long id;
    private long hostID;
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
    private boolean isHost;
    private boolean isKicked;

    public TourItem() {

    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
