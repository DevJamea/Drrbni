package com.example.drrbni.ViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbni.Models.Job;

public class ShowAndEditJobViewModel extends AndroidViewModel {

    private Repository repository;

    public ShowAndEditJobViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getJobById(String jobId ,MyListener<Job> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getJobById(jobId, isSuccessful, isFailure);
    }

    public void editJobData(String jobId , Uri image, String jobName, String major, String jobLink,
                            String jobDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure){
        repository.editJobData(jobId, image, jobName, major, jobLink, jobDescription, isSuccessful, isFailure);
    }

    public void editJobDataWithoutImg(String jobId , String jobName, String major, String jobLink,
                                      String jobDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure){
        repository.editJobDataWithoutImg(jobId, jobName, major, jobLink, jobDescription, isSuccessful, isFailure);
    }
}
