package com.example.questionsapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private static final String TAG = "Question";
    static String QUESTION_TYPE_FINAL_EXAM = "Final_Exam";
    static String QUESTION_TYPE_MIDDLE_EXAM = "Middle_Exam";
    static String QUESTION_TYPE_GENERAL = "General";

    static String QUESTION_SUBJECT_WUG = "WUG";
    static String QUESTION_SUBJECT_ITK = "ITK";
    static String QUESTION_SUBJECT_ANW = "ANW";
    static String QUESTION_SUBJECT_GENERAL_IT = "General_It";
    static String QUESTION_SUBJECT_GENERAL_ISLAMIC = "General_Islamic";


    private int id;
    private String title;
    private String type;
    private String year;
    private String subject;
    private String description;
    private String image;
    private int points;
    private boolean multipleChoice;
    private List<Choice> choices;
    private String correctAnswer;

    public Question(String title, String subject, String description, String image, int points, boolean multipleChoice, List<Choice> choices, String correctAnswer) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.image = image;
        this.points = points;
        this.multipleChoice = multipleChoice;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
        this.choices = new ArrayList<>();
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

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @param choice Question
     * @return true if added
     */
    public boolean addChoice(Choice choice) {
        boolean result = false;
        if (!this.choices.contains(choice)){
            this.choices.add(choice);
            Log.d(TAG, "Success addChoice: "+choice.getId() + ". added!!!");
            result= true;
        }else {
            Log.d(TAG, "Error addChoice: "+choice.getId() + ". Error!!!");
        }
        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", year='" + year + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", points=" + points +
                ", multipleChoice=" + multipleChoice +
                ", choices=" + choices +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
