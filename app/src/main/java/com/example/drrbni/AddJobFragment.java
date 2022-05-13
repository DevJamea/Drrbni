package com.example.drrbni;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.drrbni.databinding.FragmentAddJobBinding;


public class AddJobFragment extends Fragment {


    public AddJobFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAddJobBinding binding = FragmentAddJobBinding.inflate(getLayoutInflater());

        binding.addJobName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheet =
                        BottomSheetFragment.newInstance(getString(R.string.job_name));
                bottomSheet.show(getFragmentManager() , "tag");
            }
        });

        return binding.getRoot();
    }
}