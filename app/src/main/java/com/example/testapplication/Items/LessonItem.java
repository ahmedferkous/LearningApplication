package com.example.testapplication.Items;

import android.net.Uri;

import java.util.ArrayList;

public class LessonItem {
    private String lessonName, lessonDesc, datePosted, difficulty;
    private ArrayList<Uri> fileUris;

    public LessonItem(String lessonName, String lessonDesc, String datePosted, String difficulty, ArrayList<Uri> fileUris) {
        this.lessonName = lessonName;
        this.lessonDesc = lessonDesc;
        this.datePosted = datePosted;
        this.difficulty = difficulty;
        this.fileUris = fileUris;
    }

    public LessonItem() {
    }

    public ArrayList<Uri> getFileUris() {
        return fileUris;
    }

    public void setFileUris(ArrayList<Uri> fileUris) {
        this.fileUris = fileUris;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDesc() {
        return lessonDesc;
    }

    public void setLessonDesc(String lessonDesc) {
        this.lessonDesc = lessonDesc;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


}
