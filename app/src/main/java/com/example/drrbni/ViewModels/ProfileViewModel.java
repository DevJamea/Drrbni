package com.example.drrbni.ViewModels;

import static com.example.drrbni.Constant.ADDRESS;
import static com.example.drrbni.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.GOVERNORATE;
import static com.example.drrbni.Constant.IMG;
import static com.example.drrbni.Constant.MAJOR;
import static com.example.drrbni.Constant.NAME;
import static com.example.drrbni.Constant.WHATSAPP;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Student> profileInfo;
    private MutableLiveData<List<Job>> jobsData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        profileInfo = repository.getProfileInfo();
        jobsData = repository.getJobsData();
    }

    public void requestProfileInfo(String uid){
        repository.requestProfileInfo(uid);
    }

    public void storeJobData(String uid,Uri image, String jobName, String major, String jobLink, String jobDescription,
                             MyListener<Boolean> isSuccessful ,MyListener<Boolean> isFailure) {
        repository.storeJobData(uid, image, jobName, major, jobLink, jobDescription, isSuccessful , isFailure);
    }

    public void requestGetJobs(String uid) {
        repository.requestGetJobs(uid);
    }

    public MutableLiveData<Student> getProfileInfo() {
        return profileInfo;
    }

    public MutableLiveData<List<Job>> getJobsData() {
        return jobsData;
    }

    public void editImg(Uri image, MyListener<Boolean> isFailure) {
        repository.editImg(image, isFailure);
    }

    public void editProfile(String name,String email,String major, MyListener<Boolean> isSuccessful){
        repository.editProfile(name, email, major, isSuccessful);
    }

    public void editProfileContactInformation(String whatsapp, MyListener<Boolean> isSuccessful){
        repository.editProfileContactInformation(whatsapp, isSuccessful);
    }

    public void editAddressData(String governorate, String address, MyListener<Boolean> isSuccessful) {
        repository.editAddressData(governorate, address, isSuccessful);
    }

    public void changePassword(String currentPassword,String newPassword,String confPassword,MyListener<String > isSuccessful, MyListener<String> isFailure){
        repository.changePassword(currentPassword, newPassword, confPassword, isSuccessful, isFailure);
    }

}
