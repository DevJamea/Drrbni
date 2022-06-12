package com.example.drrbni.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import java.util.List;

public class CompanyProfileViewModel extends AndroidViewModel {

    private Repository repository;

    public CompanyProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getCompaniesByMajor(String major, MyListener<List<Company>> isSuccessful, MyListener<String> isFailure) {
        repository.getCompaniesByMajor(major, isSuccessful, isFailure);
    }

    public void getInfoCompanyByUID(String UserId, MyListener<Company> isSuccessful, MyListener<String> isFailure) {
        repository.getInfoCompanyByUID(UserId,isSuccessful,isFailure);
    }

    public void getAdsByUid(String Uid, MyListener<List<Ads>> isSuccessful, MyListener<String> isFailure) {
        repository.getAdsByUid(Uid,isSuccessful,isFailure);
    }
}
