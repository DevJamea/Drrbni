package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.ViewModels.EducationInformationViewModel;
import com.example.drrbni.ViewModels.MyListener;

import com.example.drrbni.databinding.FragmentEducationInformationBinding;
import com.google.android.material.snackbar.Snackbar;

public class EducationInformationFragment extends Fragment {

    private FragmentEducationInformationBinding binding;
    private EducationInformationViewModel educationInformationViewModel;

    public EducationInformationFragment() {}

    public static EducationInformationFragment newInstance(String param1, String param2) {
        return new EducationInformationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        educationInformationViewModel = new ViewModelProvider(this).get(EducationInformationViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationInformationBinding.inflate(getLayoutInflater(),container,false);


        binding.signUpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String college = binding.signUpCollege.getSelectedItem().toString();
                String major = binding.signUpMajor.getSelectedItem().toString();

                if (binding.signUpCollege.getSelectedItemPosition() < 1) {
                    Snackbar.make(view, "حدد الجامعة", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (binding.signUpMajor.getSelectedItemPosition() < 1) {
                    Snackbar.make(view, "أدخل التخصص", Snackbar.LENGTH_LONG).show();
                    return;
                }

                load();

                educationInformationViewModel.storeData(college, major, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value){
                            stopLoad();
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_educationInformationFragment_to_signUpContactInformationFragment);
                        }
                    }
                });

            }
        });

        return binding.getRoot();
    }

    public void load(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signUpBtnNext.setEnabled(false);
        binding.signUpBtnNext.setClickable(false);
    }

    public void stopLoad(){
        binding.progressBar.setVisibility(View.GONE);
        binding.signUpBtnNext.setEnabled(true);
        binding.signUpBtnNext.setClickable(true);
    }
}