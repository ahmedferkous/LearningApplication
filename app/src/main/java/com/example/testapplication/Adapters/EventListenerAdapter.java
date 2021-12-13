package com.example.testapplication.Adapters;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.testapplication.Interfaces.OnRetrievedImageUris;
import com.example.testapplication.Items.LessonItem;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public abstract class EventListenerAdapter implements EventListener<QuerySnapshot>, OnRetrievedImageUris {
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

    }

    @Override
    public void onImageUriListRetrievedResult(LessonItem lessonItem, int sizeOfDocuments) {

    }

}
