package com.example.testapplication.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.Utils.MD5Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class LoginRepository {
    private static final String TAG = "LoginRepository";
    public static final String USERS = "users";
    public static final String PASSWORD = "password";
    public static final String TYPE = "type";

    private ListenerRegistration listenerRegistration;
    private MutableLiveData<LoginResponse> userLiveData, userCheckLiveData;
    private FirebaseFirestore db;

    public void initFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<LoginResponse> loginWithData(String userID, String password) {
        initFirebase();
        userLiveData = new MutableLiveData<>();

        DocumentReference docRef = db.collection(USERS).document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                LoginResponse loginResponse = new LoginResponse();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        if (document.getData() != null && document.getData().get(PASSWORD).equals(toMD5(password))) {
                            loginResponse.setResponse(LoginResponse.SUCCESS);
                            loginResponse.setMd5_password(toMD5(password));
                            loginResponse.setType((String) document.getData().get(TYPE));
                        } else {
                            loginResponse.setResponse(LoginResponse.FAILED_PASSWORD);
                        }
                    } else {
                        loginResponse.setResponse(LoginResponse.FAILED_USER_ID);
                    }
                } else {
                    loginResponse.setResponse(LoginResponse.UNKNOWN_ERROR);
                }
                userLiveData.postValue(loginResponse);
            }
        });
        return userLiveData;
    }

    public MutableLiveData<LoginResponse> registerLoginDataChangeListener(String userID, String passwordMD5) {
        userCheckLiveData = new MutableLiveData<>();

        DocumentReference docRef = db.collection(USERS).document(userID);
        EventListener<DocumentSnapshot> listener = new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                LoginResponse loginResponse = new LoginResponse();
                if (value != null && value.getData() != null) {
                    if (!(value.getData().get(PASSWORD).equals(passwordMD5))) {
                        loginResponse.setResponse(LoginResponse.FORCED_SIGN_OUT);
                    }
                } else {
                    loginResponse.setResponse(LoginResponse.FORCED_SIGN_OUT);
                }

                userCheckLiveData.postValue(loginResponse);
            }
        };
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        listenerRegistration = docRef.addSnapshotListener(listener);

        return userCheckLiveData;
    }

    private static String toMD5(String password) {
        return MD5Utils.getMD5(password);
    }
}
