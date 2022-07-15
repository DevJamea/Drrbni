package com.example.drrbni.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Notification;
import com.example.drrbni.Models.Student;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {

    private Repository repository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getNotificationByUid(String uid , MyListener<List<Notification>> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getNotificationByUid(uid, isSuccessful, isFailure);
    }

    public void getSenderImgAndName(String senderUid , MyListener<Company> company){
        repository.getSenderImgAndName(senderUid , company);
    }

    public void getAdsById(String adsId , MyListener<Ads> isSuccessful){
        repository.getAdsById(adsId, isSuccessful);
    }
}
