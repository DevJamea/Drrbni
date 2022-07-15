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

    public void editProfile(String name,String college ,String major,
                            MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure){
        repository.editProfileDataWithoutImage(name,college, major, isSuccessful , isFailure);
    }

    public void editProfileDataWithImg(Uri image, String studentName,String college , String major
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.editProfileDataWithImg(image, studentName,college , major, isSuccessful, isFailure);
    }

    public void updateTopic(String oldTopic , String newTopic){
        repository.updateTopic(oldTopic , newTopic);
    }

    public void getEmail(String uid, MyListener<String> isSuccessful
            , MyListener<Boolean> isFailure) {

        repository.getEmail(uid, isSuccessful, isFailure);
    }

    public void changeEmail(String currentPassword, String newEmail, String uid, MyListener<Boolean> isSuccessful
            , MyListener<String> isFailure) {

        repository.changeEmail(currentPassword, newEmail, uid, isSuccessful, isFailure);
    }


}
