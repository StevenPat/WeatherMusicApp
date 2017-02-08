package com.example.steve.weathermusicapp.Grid;

public class Mood {
    private int imageId;
    private String title;

    public Mood(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;

    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}