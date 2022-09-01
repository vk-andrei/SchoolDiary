package com.example.schooldiary.repository;

public class CardData {

    private String title;
    private String description;
    private int image;
    private boolean like;

    public CardData(String title, String description, int image, boolean like) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
