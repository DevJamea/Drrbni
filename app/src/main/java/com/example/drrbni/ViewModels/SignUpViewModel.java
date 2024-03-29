package com.example.drrbni.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {

    private Repository repository;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void signUp(String email , String password ,
                       MyListener<FirebaseUser> isSuccessful , MyListener<String> isFailure){
        repository.signUp(email, password , isSuccessful , isFailure);
    }

    public void storeData (FirebaseUser firebaseUser ,String name
            ,MyListener<Boolean> isSuccessful  ){
        repository.storeSignUpData(firebaseUser , name  ,isSuccessful);
    }


}
