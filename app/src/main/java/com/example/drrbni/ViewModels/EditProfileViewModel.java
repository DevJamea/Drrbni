package com.example.drrbni.ViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbni.Models.Student;

public class EditProfileViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Student> profileInfo;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        profileInfo = repository.getProfileInfo();
    }

    public void requestProfileInfo(String uid){
        repository.requestProfileInfo(uid);
    }

    public MutableLiveData<Student> getProfileInfo() {
        return profileInfo;
    }

    public void editProfile(String name,String email,String major,
                            MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure){
        repository.editProfileDataWithoutImage(name, email, major, isSuccessful , isFailure);
    }

    public void editProfileDataWithImg(Uri image, String studentName, String email, String major
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.editProfileDataWithImg(image, studentName, email, major, isSuccessful, isFailure);
    }

    public void updateTopic(String oldTopic , String newTopic){
        repository.updateTopic(oldTopic , newTopic);
    }
}
