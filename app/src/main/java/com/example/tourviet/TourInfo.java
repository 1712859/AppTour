package com.example.tourviet;

public class TourInfo {
    private String name;
    private long startDate;
    private long endDate ;
    private long sourceLat;
    private long sourceLong;
    private long desLat;
    private long desLong;
    private Boolean isPrivate ;
    private long adults;
    private long childs;
    private long minCost;
    private long maxCost;
    private String avatar;

    public TourInfo(String name, long startDate, long endDate, long sourceLat, long sourceLong, long desLat, long desLong, Boolean isPrivate, long adults, long childs, long minCost, long maxCost, String avatar) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
    }
}
