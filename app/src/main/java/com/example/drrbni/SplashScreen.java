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

public class SplashScreen extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int SPLASH_SCREEN_TIME_OUT = 2500;


    private String mParam1;
    private String mParam2;


    public SplashScreen() {}

    public static SplashScreen newInstance(String param1, String param2) {
        SplashScreen fragment = new SplashScreen();
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

        FragmentSplashScreenBinding binding = FragmentSplashScreenBinding.inflate
                (getLayoutInflater(),container,false);

//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Intent is used to switch from one activity to another.
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_splashScreen_to_onboarding);

            }
        }, SPLASH_SCREEN_TIME_OUT);

        return binding.getRoot();
    }
}