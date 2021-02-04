package com.example.questionsapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private static final String TAG = "Scenario";
    static String SCENARIO_SUBJECT_WUG = "WUG";
    static String SCENARIO_SUBJECT_ITK = "ITK";
    static String SCENARIO_SUBJECT_ANW = "ANW";


    private int id;
    private String title;
    private String subject;
    private String description;
    private String image;
    private int points;
    private List<Question> questions;


    public Scenario() {
        this.questions = new ArrayList<>();
    }

    public Scenario(String title, String subject, String description, String image, int points, List<Question> questions) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.image = image;
        this.points = points;
        this.questions = questions;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @param question Question
     * @return true if added
     */
    public boolean addQuestion(Question question) {
        boolean result = false;
        if (!this.questions.contains(question)){
            this.questions.add(question);
            Log.d(TAG, "Success addQuestion: "+question.getId() + ". added!!!");
            result= true;
        }else {
            Log.d(TAG, "Error addQuestion: "+question.getId() + ". Error!!!");
        }
        return result;
    }
}
