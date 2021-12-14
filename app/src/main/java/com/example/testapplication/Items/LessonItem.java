package com.example.testapplication.Items;

import android.net.Uri;

import java.util.ArrayList;

public class LessonItem {
    public static final String NOTES = "notes";
    public static final String DATE_POSTED = "datePosted";
    public static final String DIFFICULTY = "difficulty";
    public static final String LESSON_DESCRIPTION = "lessonDescription";
    public static final String LESSON_NAME = "lessonName";

    private final String uniqueDocumentId;
    private String lessonName, lessonDesc, datePosted, difficulty;
    private ArrayList<Uri> fileUris;

    public LessonItem(String uniqueDocumentId) {
        this.uniqueDocumentId = uniqueDocumentId;
    }

    public String getUniqueDocumentId() {
        return uniqueDocumentId;
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
