package com.example.tourviet;

public class add_member_class {
    private String tourId;
    private String invitedUserId;
    private Boolean isInvited;

    public add_member_class(String tourId, String invitedUserId, Boolean isInvited) {
        this.tourId = tourId;
        this.invitedUserId = invitedUserId;
        this.isInvited = isInvited;
    }
}
