package com.example.drrbni.ViewModels;

import static com.example.drrbni.Constant.COLLECTION_MAJORS;
import static com.example.drrbni.Constant.MAJOR_NAME;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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
