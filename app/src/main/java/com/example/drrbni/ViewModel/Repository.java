package com.example.drrbni.ViewModel;

import android.app.Application;

import static com.example.drrbni.Constant.ADDRESS;
import static com.example.drrbni.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbni.Constant.COLLEGE;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.GOVERNORATE;
import static com.example.drrbni.Constant.MAJOR;
import static com.example.drrbni.Constant.NAME;
import static com.example.drrbni.Constant.STUDENT_TYPE;
import static com.example.drrbni.Constant.TYPE_USER;
import static com.example.drrbni.Constant.UID;
import static com.example.drrbni.Constant.WHATSAPP;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Repository {

    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public Repository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    public void register (String email , String password ,
                          MyListener<FirebaseUser> isSuccessful , MyListener<String> isFailure){

        firebaseAuth.createUserWithEmailAndPassword(email , password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            isSuccessful.onValuePosted(firebaseAuth.getCurrentUser());
                        }else {
                            isFailure.onValuePosted(task.getException().toString());
                        }
                    }
                });
    }

    public void storeNo1 (FirebaseUser firebaseUser ,String name ,MyListener<Boolean> isSuccessful  ){

        HashMap<String , Object> data = new HashMap<>();
        data.put(UID , firebaseUser.getUid());
        data.put(NAME , name);
        data.put(EMAIL , firebaseUser.getEmail());
        data.put(TYPE_USER , STUDENT_TYPE);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).document(firebaseUser.getUid())
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);
            }
        });
    }

    public void storeNo2 (String governorate , String address , MyListener<Boolean> isSuccessful){

        HashMap<String , Object> data = new HashMap<>();
        data.put(GOVERNORATE , governorate);
        data.put(ADDRESS , address);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).
                document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);

            }
        });

    }

    public void storeNo3 (String college , String major , MyListener<Boolean> isSuccessful){

        HashMap<String , Object> data = new HashMap<>();
        data.put(COLLEGE , college);
        data.put(MAJOR , major);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);

            }
        });

    }

    public void storeNo4 (String whatsapp  , MyListener<Boolean> isSuccessful){

        HashMap<String , Object> data = new HashMap<>();
        data.put(WHATSAPP , whatsapp);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                    }
                });
    }

}
