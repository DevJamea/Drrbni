package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentSplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenFragment extends Fragment {

    private FirebaseAuth mAuth;
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    public SplashScreenFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSplashScreenBinding binding = FragmentSplashScreenBinding.inflate
                (getLayoutInflater(),container,false);

        mAuth = FirebaseAuth.getInstance();

//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (mAuth.getCurrentUser() != null){
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(R.id.action_splashScreenFragment_to_homeActivity);
                }else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(R.id.action_splashScreen_to_onboarding);
                }
            }
        },SPLASH_SCREEN_TIME_OUT);

        return binding.getRoot();
    }
}