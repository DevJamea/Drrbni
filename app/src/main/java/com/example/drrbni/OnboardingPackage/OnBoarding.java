package com.example.drrbni.OnboardingPackage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.drrbni.Adapters.OnBoardingViewPagerAdapter;
import com.example.drrbni.Models.OnBoardingItem;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentOnboardingBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FragmentOnboardingBinding binding;
    OnBoardingViewPagerAdapter onBoardingViewPagerAdapter ;
    int position = 0;
    Animation btnAnim;

    public OnBoarding() {}

    public static OnBoarding newInstance(String param1, String param2) {
        OnBoarding fragment = new OnBoarding();
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

        binding = FragmentOnboardingBinding.inflate
                (getLayoutInflater(),container,false);

        btnAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.button_animation);

        if (restorePrefData()) {

//            NavController navController = Navigation.findNavController(binding.getRoot());
//            navController.navigate(R.id.action_onboarding_to_home);
        }


        final List<OnBoardingItem> mList = new ArrayList<>();
        mList.add(new OnBoardingItem(R.string.onboarding1_title,R.string.onboarding1_subtitle, R.drawable.onboarding1));
        mList.add(new OnBoardingItem(R.string.onboarding2_title,R.string.onboarding2_subtitle, R.drawable.onboarding2));
        mList.add(new OnBoardingItem(R.string.onboarding3_title,R.string.onboarding3_subtitle, R.drawable.onboarding3));
        mList.add(new OnBoardingItem(R.string.onboarding4_title,R.string.onboarding4_subtitle, R.drawable.onboarding4));

        onBoardingViewPagerAdapter = new OnBoardingViewPagerAdapter(getContext(), mList);
        binding.onboardingSlider.setAdapter(onBoardingViewPagerAdapter);

        binding.dotSlider.setupWithViewPager(binding.onboardingSlider);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = binding.onboardingSlider.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    binding.onboardingSlider.setCurrentItem(position);
                }

                if (position == mList.size()-1) {
                    loaddLastScreen();
                }
            }
        });

        binding.dotSlider.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_onboarding_to_login);
                savePrefsData();
            }
        });

        return binding.getRoot();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = requireContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = requireContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    private void loaddLastScreen() {
        binding.btnNext.setVisibility(View.INVISIBLE);
        binding.getStarted.setVisibility(View.VISIBLE);
        binding.dotSlider.setVisibility(View.INVISIBLE);
        binding.getStarted.setAnimation(btnAnim);
    }

}

