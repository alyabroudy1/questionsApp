package com.example.questionsapp;

import java.util.List;

public class Choice {

    private static final String TAG = "Choice";
    private int id;
    private String title;
    private String description;
    private String image;
    private boolean correct;

    public Choice() {
    }

    public Choice(String title, String description, String image, boolean correct) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
