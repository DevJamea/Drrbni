package com.example.drrbni.ViewModel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LiveDataListener extends LiveData<DocumentSnapshot> implements EventListener<QuerySnapshot> {
    private Query query;
    private ListenerRegistration listener;
    private MutableLiveData<List<QueryDocumentSnapshot>> listMutableLiveData;

    public LiveDataListener(Query query){
        this.query = query;
        listMutableLiveData = new MutableLiveData<>();
        listener = this.query.addSnapshotListener(this);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if (value != null && error==null){
            ArrayList<QueryDocumentSnapshot> myList = new ArrayList<>();
            for (QueryDocumentSnapshot snapshot:value){
                myList.add(snapshot);
            }
            listMutableLiveData.setValue(myList);
        }else {
            setLog(error.getMessage());
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        listener = query.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        listener.remove();
        listener = null;
    }

    public MutableLiveData<List<QueryDocumentSnapshot>> getLiveData (){
        return listMutableLiveData;
    }

    public void setLog(String message){
        Log.d("ttt",message);
    }
}
