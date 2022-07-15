package com.example.drrbni.ViewModels;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbni.Models.Company;

public class HomeViewModel extends AndroidViewModel {

    private Repository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getCompany(String uid , MyListener<Company> company){
        repository.getCompany(uid, company);
    }

    public void SignOut(Activity activity){
        repository.SignOut(activity);
    }


}
