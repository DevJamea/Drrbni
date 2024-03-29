package com.example.drrbni.ViewModels;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbni.Models.Student;

public class SignInViewModel extends AndroidViewModel {

    private Repository repository;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void signIn(String email ,String password, MyListener<Boolean> isSuccessful
            , MyListener<String> isFailure){
        repository.signIn(email, password, isSuccessful, isFailure);
    }

    public void checkSignInData(String email , MyListener<Student> isSuccessful , MyListener<Boolean> isFailure){
        repository.checkSignInData(email , isSuccessful , isFailure);
    }

    public void resetPassword(String email, MyListener<Boolean> isSuccessful, MyListener<String > isFailure){
        repository.resetPassword(email, isSuccessful, isFailure);
    }

    public void SignOut(Activity activity){
        repository.SignOut(activity);
    }

}
