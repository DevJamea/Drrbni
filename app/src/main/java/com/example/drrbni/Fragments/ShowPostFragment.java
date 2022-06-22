package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.databinding.FragmentShowPostBinding;

public class ShowPostFragment extends Fragment {

    private FragmentShowPostBinding binding;
    private Ads ads;
    public ShowPostFragment() {}

    public static ShowPostFragment newInstance() {
        return new ShowPostFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ads = (Ads) getArguments().getSerializable("adsObject");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPostBinding
                .inflate(getLayoutInflater(),container,false);

        binding.postTitle.setText(ads.getAdsTitle());
        binding.postDescription.setText(ads.getAdsDescription());
        binding.postRequirements.setText(ads.getAdsRequirements());

        binding.trainingRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo send notification to this company
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