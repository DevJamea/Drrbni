package com.example.drrbni.OnboardingPackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentOnboardingBinding;
import com.example.drrbni.databinding.FragmentOnboardingContainerBinding;

public class OnBoardingContainer extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OnBoardingContainer() {
    }

    public static OnBoardingContainer newInstance(String param1, String param2) {
        OnBoardingContainer fragment = new OnBoardingContainer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentOnboardingContainerBinding binding = FragmentOnboardingContainerBinding.inflate
                (getLayoutInflater(), container, false);


        return binding.getRoot();
    }
}