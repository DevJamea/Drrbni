package com.example.drrbni.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ShowPostFragmentViewModel extends AndroidViewModel {

    private Repository repository;

    public ShowPostFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getTokenByCompanyId(String companyId, MyListener<String> isSuccessful){
        repository.getTokenByCompanyId(companyId , isSuccessful);
    }

    public void getNameByUid(String Uid , MyListener<String> isSuccessful){
        repository.getNameByUid(Uid , isSuccessful);
    }

    public void addToRequestArray(String adsId){
        repository.addToRequestArray(adsId);
    }
}
