package com.example.testapplication.Repositories;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.Interfaces.OnRetrievedImageUris;
import com.example.testapplication.Items.LessonItem;
import com.example.testapplication.Items.Question;
import com.example.testapplication.Items.TestItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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

import static com.example.testapplication.Items.LessonItem.DATE_POSTED;
import static com.example.testapplication.Items.LessonItem.DIFFICULTY;
import static com.example.testapplication.Items.LessonItem.LESSON_DESCRIPTION;
import static com.example.testapplication.Items.LessonItem.LESSON_NAME;
import static com.example.testapplication.Items.LessonItem.NOTES;
import static com.example.testapplication.Items.Question.POSITION;
import static com.example.testapplication.Items.Question.QUESTION;
import static com.example.testapplication.Items.Question.TYPE;
import static com.example.testapplication.Items.TestItem.QUESTIONS;
import static com.example.testapplication.Items.TestItem.TESTS;
import static com.example.testapplication.Items.TestItem.TEST_DESCRIPTION;
import static com.example.testapplication.Items.TestItem.TEST_NAME;
import static com.example.testapplication.Items.TestItem.USERS_ASSIGNED;

public class FirebaseRepository  {
    private static final String TAG = "FirebaseRepository";

    private List<TestItem> testItemList;
    private List<LessonItem> lessonItemList;
    private final MutableLiveData<List<LessonItem>> lessonItemListMutableLiveData;
    private final MutableLiveData<List<TestItem>> testItemListMutableLiveData;
    private FirebaseFirestore firebaseFirestore;

    public FirebaseRepository() {
        this.lessonItemListMutableLiveData = new MutableLiveData<>();
        this.testItemListMutableLiveData = new MutableLiveData<>();
    }

    private void initDb() {
        if (firebaseFirestore == null) {
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
    }

    public MutableLiveData<List<TestItem>> getTestItemListMutableLiveData(String userID) {
        initDb();
        CollectionReference testsCollectionReference = firebaseFirestore.collection(TESTS);
        testsCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent: ER>#$IH&%#");
                testItemList = new ArrayList<>();
                List<DocumentSnapshot> testItemDocumentSnapshotList = value.getDocuments();
                for (int i = 0; i < testItemDocumentSnapshotList.size(); i++) {
                    DocumentSnapshot testItemDocument = testItemDocumentSnapshotList.get(i);
                    String documentId = testItemDocument.getId();

                    TestItem testItem = new TestItem();

                    Map<String, Object> testItemDocumentMap = testItemDocument.getData();
                    testItem.setTestName((String) testItemDocumentMap.get(TEST_NAME));
                    testItem.setTestDescription((String) testItemDocumentMap.get(TEST_DESCRIPTION));

                    DocumentReference testDocumentReference = testsCollectionReference.document(documentId);
                    CollectionReference questionsCollectionReference = testDocumentReference.collection(QUESTIONS);
                    DocumentReference userAssignedDocumentReference = testDocumentReference.collection(USERS_ASSIGNED).document(userID);

                    questionsCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            ArrayList<Question> questionsList = new ArrayList<>();
                            List<DocumentSnapshot> questionsDocumentSnapshotList = value.getDocuments();

                            for (int j = 0; j < questionsDocumentSnapshotList.size(); j++) {
                                DocumentSnapshot questionDocument = questionsDocumentSnapshotList.get(j);

                                Question questionItem = null;

                                Map<String, Object> questionDocumentMap = questionDocument.getData();
                                String type = (String) questionDocumentMap.get(TYPE);
                                String question = (String) questionDocumentMap.get(QUESTION);
                                Long position = (Long) questionDocumentMap.get(POSITION);

                                switch (type) {
                                    case Question.ShortAnswer.SHORT_ANSWER:
                                        String correctShortAnswer = (String) questionDocumentMap.get(Question.ShortAnswer.CORRECT_ANSWER);
                                        questionItem = new Question.ShortAnswer(type, question, position, correctShortAnswer);
                                        break;
                                    case Question.MultipleChoice.MULTIPLE_CHOICE:
                                        String correctMultipleChoice = (String) questionDocumentMap.get(Question.MultipleChoice.CORRECT_ANSWER);
                                        ArrayList<String> multipleChoiceChoices = (ArrayList<String>) questionDocumentMap.get(Question.MultipleChoice.CHOICES);
                                        questionItem = new Question.MultipleChoice(type, question, position, multipleChoiceChoices, correctMultipleChoice);
                                        break;
                                    case Question.CheckList.CHECKLIST:
                                        ArrayList<String> checklistChoices = (ArrayList<String>) questionDocumentMap.get(Question.CheckList.CHOICES);
                                        ArrayList<String> correctAnswers = (ArrayList<String>) questionDocumentMap.get(Question.CheckList.CORRECT_ANSWERS);
                                        questionItem = new Question.CheckList(type, question, position, checklistChoices, correctAnswers);
                                        break;
                                    default:
                                        break;
                                }
                                if (questionItem != null) {
                                    questionsList.add(questionItem);
                                }
                            }
                            testItem.setQuestions(questionsList);
                            userAssignedDocumentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    Map<String, Object> userAssignedDocumentMap = value.getData();
                                    Map<Integer, Object> answeredQuestions = (Map<Integer, Object>) userAssignedDocumentMap.get(TestItem.AssignedUser.ANSWERED_QUESTIONS);
                                    String progress = (String) userAssignedDocumentMap.get(TestItem.AssignedUser.PROGRESS);
                                    TestItem.AssignedUser assignedUser = new TestItem.AssignedUser(answeredQuestions, progress);

                                    testItem.setAssignedUser(assignedUser);
                                    testItemList.add(testItem);
                                    if (testItemList.size() == testItemDocumentSnapshotList.size()) {
                                        testItemListMutableLiveData.postValue(testItemList);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        return testItemListMutableLiveData;
    }

    public MutableLiveData<List<LessonItem>> getLessonItemListMutableLiveData() {
        initDb();
        CollectionReference collectionReference = firebaseFirestore.collection(NOTES);
        collectionReference.addSnapshotListener(new OnRetrievedImageUris() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                lessonItemList = new ArrayList<>();
                List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                for (int i = 0; i < documentSnapshotList.size(); i++) {
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
