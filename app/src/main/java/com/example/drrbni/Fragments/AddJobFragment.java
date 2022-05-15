package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentAddJobBinding;

public class AddJobFragment extends Fragment {

    private FragmentAddJobBinding binding;
    public AddJobFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddJobBinding
                .inflate(getLayoutInflater(),container,false);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}