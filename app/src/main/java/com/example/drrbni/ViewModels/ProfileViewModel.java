package com.example.drrbni.ViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Student;

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


    public void deleteJob(String jobId, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.deleteJob(jobId, isSuccessful, isFailure);
    }




}
