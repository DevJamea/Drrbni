package com.example.drrbni.ViewModel;


import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private LiveData<List<QueryDocumentSnapshot>> model;
    private Repository repository;

    public void init(String collectionPath,String filedName,Object value){
        if (model != null){
            return;
        }
        repository = Repository.getInstance();
        model = repository.getDataWhereEqualTo(collectionPath,filedName,value);
    }

    public LiveData<List<QueryDocumentSnapshot>> getData (){
        return model;
    }

}
