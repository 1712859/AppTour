package com.example.tourviet;

class CommentBody {

    private Long tourId;
    private long userId;
    private String comment;

    public CommentBody(long tourId, long userId, String comment) {
        this.tourId = tourId;
        this.userId = userId;
        this.comment = comment;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
