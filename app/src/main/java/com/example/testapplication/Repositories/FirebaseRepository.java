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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
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

    private final MutableLiveData<List<LessonItem>> lessonItemListMutableLiveData;
    private final FirebaseFirestore firebaseFirestore;

    public FirebaseRepository() {
        this.lessonItemListMutableLiveData = new MutableLiveData<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<LessonItem>> getLessonItemListMutableLiveData() {
        List<LessonItem> lessonItemList = new ArrayList<>();

        CollectionReference collectionReference = firebaseFirestore.collection(NOTES);
        collectionReference.addSnapshotListener(new EventListenerAdapter() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentChange> documentChangeList = value.getDocumentChanges();

                for (int i = 0; i < documentChangeList.size(); i++) {
                    Log.d(TAG, "onEvent: " + documentChangeList.size() + " " + i);
                    DocumentChange c = documentChangeList.get(i);
                    boolean isFinalItem = i == (documentChangeList.size() - 1);

                    LessonItem lessonItem = new LessonItem();

                    Map<String, Object> documentMap = c.getDocument().getData();
                    lessonItem.setDatePosted((String) documentMap.get(DATE_POSTED));
                    lessonItem.setDifficulty((String) documentMap.get(DIFFICULTY));
                    lessonItem.setLessonDesc((String) documentMap.get(LESSON_DESCRIPTION));
                    lessonItem.setLessonName((String) documentMap.get(LESSON_NAME));

                    String documentId = c.getDocument().getId();
                    new RetrieveImageAsyncTask(this, lessonItem, isFinalItem).execute(documentId);
                }

            }

            @Override
            public synchronized void onImageUrisRetrievedResult(LessonItem lessonItem, boolean lastItem) {
                lessonItemList.add(lessonItem);
                if (lastItem) {
                    lessonItemListMutableLiveData.postValue(lessonItemList);
                }
            }
        });
        return lessonItemListMutableLiveData;
    }

    private static class RetrieveImageAsyncTask extends AsyncTask<String, List<Uri>, Void> {
        private final OnRetrievedImageUris onRetrievedImageUris;
        private final LessonItem lessonItem;
        private final boolean finalImageItemRetrieval;

        public RetrieveImageAsyncTask(OnRetrievedImageUris onRetrievedImageUris, LessonItem lessonItem, boolean finalImageItemRetrieval) {
            this.onRetrievedImageUris = onRetrievedImageUris;
            this.lessonItem = lessonItem;
            this.finalImageItemRetrieval = finalImageItemRetrieval;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String documentId = strings[0];

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(NOTES).child(documentId);
            storageReference.listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
                @Override
                public void onComplete(@NonNull Task<ListResult> task) {
                    ArrayList<Uri> uriList = new ArrayList<>();
                    List<StorageReference> imageStorageRefsList = task.getResult().getItems();

                    for (StorageReference ref : imageStorageRefsList) {
                        String imageUri = ref.getName();
                        //Uri imageUri = ref.getDownloadUrl().getResult()
                        uriList.add(Uri.parse(imageUri));
                    }

                    lessonItem.setFileUris(uriList);
                    onRetrievedImageUris.onImageUrisRetrievedResult(lessonItem, finalImageItemRetrieval);
                }
            });
            return null;
        }
    }

}
