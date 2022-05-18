package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentEducationInformationBinding;
import com.google.android.material.snackbar.Snackbar;

public class EducationInformationFragment extends Fragment {

    private FragmentEducationInformationBinding binding;
    public EducationInformationFragment() {}

    public static EducationInformationFragment newInstance(String param1, String param2) {
        EducationInformationFragment fragment = new EducationInformationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationInformationBinding.inflate(getLayoutInflater(),container,false);

        String name = EducationInformationFragmentArgs.fromBundle(requireArguments()).getName();
        String email = EducationInformationFragmentArgs.fromBundle(requireArguments()).getEmail();
        String password = EducationInformationFragmentArgs.fromBundle(requireArguments()).getPassword();
        String category = EducationInformationFragmentArgs.fromBundle(requireArguments()).getCategory();
        String governorate = EducationInformationFragmentArgs.fromBundle(requireArguments()).getGovernorate();
        String address = EducationInformationFragmentArgs.fromBundle(requireArguments()).getAddress();

        binding.signUpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String university = binding.signUpEtUniversity.getText().toString().trim();
                String specialization = binding.signUpEtSpecialization.getText().toString().trim();

                if (university.isEmpty()) {
                    Snackbar.make(view, "حدد الجامعة", Snackbar.LENGTH_LONG).show();
                } else if (specialization.isEmpty()) {
                    Snackbar.make(view, "أدخل التخصص", Snackbar.LENGTH_LONG).show();
                } else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(EducationInformationFragmentDirections.actionEducationInformationFragmentToSignUpContactInformationFragment(
                            name, email, password, category, governorate, address,university,specialization));
                }
            }
        });

        return binding.getRoot();
    }
}