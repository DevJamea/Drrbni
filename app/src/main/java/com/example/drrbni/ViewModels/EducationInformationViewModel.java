package com.example.drrbni.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class EducationInformationViewModel extends AndroidViewModel {

    private Repository repository;

    public EducationInformationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void storeData (String college , String major , MyListener<Boolean> isSuccessful){
        repository.storeEducationData(college , major , isSuccessful);
    }

    public void getColleges(MyListener<List<String>> isSuccessful) {
        repository.getColleges(isSuccessful);
    }

    public void getMajors(MyListener<List<String>> isSuccessful) {
        repository.getMajors(isSuccessful);
    }


}
