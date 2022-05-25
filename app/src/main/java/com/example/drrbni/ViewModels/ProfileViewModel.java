package com.example.drrbni.ViewModels;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Student;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private Repository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getInfoProfile(String uid , MyListener<Student> isSuccessful , MyListener<Boolean> isFailure){
        repository.getInfoProfile(uid, isSuccessful, isFailure);
    }

    public void storeJobData(String uid,Uri image, String jobName, String major, String jobLink, String jobDescription, MyListener<Boolean> isSuccessful) {
        repository.storeJobData(uid, image, jobName, major, jobLink, jobDescription, isSuccessful);
    }

    public void getJobs(String uid, MyListener<List<Job>> isSuccessful, MyListener<Boolean> isFailure) {
        repository.getJobs(uid, isSuccessful, isFailure);
    }


}
