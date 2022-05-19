package com.example.drrbni.ViewModel;


import static com.example.drrbni.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.MAJOR;
import static com.example.drrbni.Constant.UID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void register (String email , String password ,
                          MyListener<FirebaseUser> isSuccessful , MyListener<String> isFailure){
        repository.register(email, password , isSuccessful , isFailure);
    }

    public void storeNo1 (FirebaseUser firebaseUser ,String name ,MyListener<Boolean> isSuccessful  ){
       repository.storeNo1(firebaseUser , name , isSuccessful);
    }

    public void storeNo2 (String governorate , String address , MyListener<Boolean> isSuccessful){
        repository.storeNo2(governorate , address , isSuccessful);
    }

    public void storeNo3 (String college , String major , MyListener<Boolean> isSuccessful){
        repository.storeNo3(college , major , isSuccessful);
    }

    public void storeNo4 (String whatsapp  , MyListener<Boolean> isSuccessful){
        repository.storeNo4(whatsapp , isSuccessful);
    }

}
