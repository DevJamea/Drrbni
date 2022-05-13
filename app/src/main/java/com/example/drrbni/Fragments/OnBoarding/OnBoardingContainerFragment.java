package com.example.drrbni.Fragments.OnBoarding;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.databinding.FragmentOnboardingContainerBinding;

public class OnBoardingContainerFragment extends Fragment {

    private static final String SCREEN_IMG = "screen_img";
    private static final String TITLE = "title";
    private static final String SUB_TITLE = "sub_title";

    private int screen_img;
    private int title;
    private int sub_title;

    public OnBoardingContainerFragment() {
        // Required empty public constructor
    }


    public static OnBoardingContainerFragment newInstance(int screen_img, int title , int sub_title) {
        OnBoardingContainerFragment fragment = new OnBoardingContainerFragment();
        Bundle args = new Bundle();
        args.putInt(SCREEN_IMG, screen_img);
        args.putInt(TITLE, title);
        args.putInt(SUB_TITLE, sub_title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            screen_img = getArguments().getInt(SCREEN_IMG);
            title = getArguments().getInt(TITLE);
            sub_title = getArguments().getInt(SUB_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOnboardingContainerBinding binding =
                FragmentOnboardingContainerBinding.inflate(getLayoutInflater());

        binding.onboardingImage.setImageResource(screen_img);
        binding.onboardingTitle.setText(title);
        binding.onboardingSubtitle.setText(sub_title);

        return binding.getRoot();
    }
}