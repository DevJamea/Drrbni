package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.databinding.FragmentSignUpAddImgBinding;

public class SignUpAddImgFragment extends Fragment {

    private FragmentSignUpAddImgBinding binding;

    public SignUpAddImgFragment() {}

    public static SignUpAddImgFragment newInstance() {
        return new SignUpAddImgFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpAddImgBinding
                .inflate(getLayoutInflater(), container, false);


        AddImgFragment dialogAddImg = new AddImgFragment();

        binding.addImgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.addImgBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}