package com.example.drrbni.ViewModel;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class Repository {
    private static Repository instance;
    public static Repository getInstance(){
        if (instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<List<QueryDocumentSnapshot>> getDataWhereEqualTo(String collectionPath,String filedName,Object value){
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(collectionPath);
        Query query = collectionReference.whereEqualTo(filedName,value);
        LiveDataListener liveData = new LiveDataListener(query);
        return liveData.getLiveData();
    }

}
