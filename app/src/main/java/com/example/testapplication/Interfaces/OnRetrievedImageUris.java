package com.example.testapplication.Interfaces;

import android.net.Uri;

import com.example.testapplication.Items.LessonItem;

import java.util.List;

public interface OnRetrievedImageUris {
    void onImageUriListRetrievedResult(LessonItem lessonItem, int sizeOfDocuments);
}
