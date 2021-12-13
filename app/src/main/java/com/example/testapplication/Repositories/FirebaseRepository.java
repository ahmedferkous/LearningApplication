package com.example.testapplication.Repositories;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.Adapters.EventListenerAdapter;
import com.example.testapplication.Interfaces.OnRetrievedImageUris;
import com.example.testapplication.Items.LessonItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseRepository  {
    private static final String TAG = "FirebaseRepository";

    public static final String NOTES = "notes";
    public static final String DATE_POSTED = "datePosted";
    public static final String DIFFICULTY = "difficulty";
    public static final String LESSON_DESCRIPTION = "lessonDescription";
    public static final String LESSON_NAME = "lessonName";

    private List<LessonItem> lessonItemList;
    private final MutableLiveData<List<LessonItem>> lessonItemListMutableLiveData;
    private final FirebaseFirestore firebaseFirestore;

    public FirebaseRepository() {
        this.lessonItemListMutableLiveData = new MutableLiveData<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<LessonItem>> getLessonItemListMutableLiveData() {
        CollectionReference collectionReference = firebaseFirestore.collection(NOTES);
        collectionReference.addSnapshotListener(new EventListenerAdapter() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                lessonItemList = new ArrayList<>();
                List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                for (int i = 0; i < documentSnapshotList.size(); i++) {
                    Log.d(TAG, "onEvent: " + documentSnapshotList.size() + " " + i);
                    DocumentSnapshot c = documentSnapshotList.get(i);
                    String documentId = c.getId();

                    LessonItem lessonItem = new LessonItem(documentId);

                    Map<String, Object> documentMap = c.getData();
                    lessonItem.setDatePosted((String) documentMap.get(DATE_POSTED));
                    lessonItem.setDifficulty((String) documentMap.get(DIFFICULTY));
                    lessonItem.setLessonDesc((String) documentMap.get(LESSON_DESCRIPTION));
                    lessonItem.setLessonName((String) documentMap.get(LESSON_NAME));

                    RetrieveImage retrieveImage = new RetrieveImage(this, lessonItem, documentSnapshotList.size());
                    retrieveImage.get(documentId);
                }

            }

            @Override
            public void onImageUriListRetrievedResult(LessonItem lessonItem, int sizeOfDocuments) {
                lessonItemList.add(lessonItem);
                if (lessonItemList.size() == sizeOfDocuments) {
                    lessonItemListMutableLiveData.postValue(lessonItemList);
                }
            }
        });
        return lessonItemListMutableLiveData;
    }

    private static class RetrieveImage implements OnCompleteListener<Uri> {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            uriList.add(task.getResult());
            if (uriList.size() == imageRefListSize) {
                lessonItem.setFileUris(uriList);
                onRetrievedImageUris.onImageUriListRetrievedResult(lessonItem, sizeOfDocuments);
            }
        }

        private final OnRetrievedImageUris onRetrievedImageUris;
        private final LessonItem lessonItem;
        private final ArrayList<Uri> uriList;
        private int imageRefListSize;
        private final int sizeOfDocuments;

        public RetrieveImage(OnRetrievedImageUris onRetrievedImageUris, LessonItem lessonItem, int sizeOfDocuments) {
            this.onRetrievedImageUris = onRetrievedImageUris;
            this.lessonItem = lessonItem;
            this.sizeOfDocuments = sizeOfDocuments;
            this.uriList = new ArrayList<>();
        }

        public void get(String documentId) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(NOTES).child(documentId);
            storageReference.listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
                @Override
                public void onComplete(@NonNull Task<ListResult> task) {
                    List<StorageReference> imageStorageRefsList = task.getResult().getItems();
                    imageRefListSize = imageStorageRefsList.size();

                    for (StorageReference ref : imageStorageRefsList) {
                        ref.getDownloadUrl().addOnCompleteListener(RetrieveImage.this);
                    }
                }
            });
        }

    }

}
