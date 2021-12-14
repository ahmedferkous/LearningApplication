package com.example.testapplication.Interfaces;

import android.net.Uri;

import com.example.testapplication.Items.LessonItem;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface OnRetrievedImageUris extends EventListener<QuerySnapshot> {
    void onImageUriListRetrievedResult(LessonItem lessonItem, int sizeOfDocuments);

}
