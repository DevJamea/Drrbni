package com.example.drrbni.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Job;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentShowAndEditJobBinding;
import com.google.android.material.snackbar.Snackbar;

public class ShowAndEditJobFragment extends Fragment {

    private FragmentShowAndEditJobBinding binding;
    private ProfileViewModel profileViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;

    public ShowAndEditJobFragment() {
    }

    public static ShowAndEditJobFragment newInstance(String param1, String param2) {
        return new ShowAndEditJobFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getImg = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            binding.imageSlider.setImageURI(result);
                            image = result;
                        }
                    }
                });

        permission = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result)
                            getImg.launch("image/*");

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowAndEditJobBinding.inflate(getLayoutInflater(), container, false);

        load();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        String jobId = getArguments().getString("jobId");

        profileViewModel.getJobById(jobId, new MyListener<Job>() {
            @Override
            public void onValuePosted(Job value) {
//                if (value.getImg() == null) {
//
//                } else {
//                    Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.imageSlider);
//                }
                binding.etJobName.setText(value.getJobName());
                binding.etDescription.setText(value.getJobDescription());
                binding.etJobLink.setText(value.getJobLink());
                stopLoad();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {
                Snackbar.make(requireView(), "فشل التحمبل", Snackbar.LENGTH_LONG).show();
                binding.shimmerView.stopShimmerAnimation();
            }
        });

        binding.imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.ads.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.ads.setVisibility(View.VISIBLE);
    }
}