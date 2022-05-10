package com.example.drrbni;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.drrbni.databinding.FragmentSplashScreenBinding;

public class SplashScreenFragment extends Fragment {


    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSplashScreenBinding binding = FragmentSplashScreenBinding.inflate
                (getLayoutInflater(),container,false);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_splashScreen_to_onboarding);
            }
        }, 5000);

        return binding.getRoot();
    }
}