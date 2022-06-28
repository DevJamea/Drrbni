package com.example.drrbni.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Job;
import com.example.drrbni.R;
import com.example.drrbni.SpinnerPosition;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.ViewModels.ShowAndEditJobViewModel;
import com.example.drrbni.databinding.FragmentShowAndEditJobBinding;
import com.google.android.material.snackbar.Snackbar;

public class ShowAndEditJobFragment extends Fragment {

    private FragmentShowAndEditJobBinding binding;
    private ShowAndEditJobViewModel showAndEditJobViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;
    private SpinnerPosition spinnerPosition;

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
                            binding.jobImage.setImageURI(result);
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

        showAndEditJobViewModel = new ViewModelProvider(this).get(ShowAndEditJobViewModel.class);
        spinnerPosition = SpinnerPosition.getInstance();

        String jobId = getArguments().getString("jobId");

        showAndEditJobViewModel.getJobById(jobId, new MyListener<Job>() {
            @Override
            public void onValuePosted(Job value) {
                Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.jobImage);
                binding.etJobName.setText(value.getJobName());
                binding.major.setSelection(spinnerPosition.getMajorPosition(value.getMajor()));
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

        binding.jobImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

                String jobName = binding.etJobName.getText().toString().trim();
                String major = binding.major.getSelectedItem().toString();
                String jobLink = binding.etJobLink.getText().toString().trim();
                String jobDescription = binding.etDescription.getText().toString().trim();

                if (TextUtils.isEmpty(jobName)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل اسم العمل", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (binding.major.getSelectedItemPosition() < 1) {
                    stopLoad();
                    Snackbar.make(view, "أدخل التخصص", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(jobLink)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل رابط العمل", Snackbar.LENGTH_LONG).show();
                    return;
                }else if (!URLUtil.isValidUrl(jobLink)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل رابط صالح", Snackbar.LENGTH_LONG).show();
                    return;
                }else if (TextUtils.isEmpty(jobDescription)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل وصف العمل", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (image != null){

                    showAndEditJobViewModel.editJobData(jobId, image, jobName, major, jobLink
                            , jobDescription, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                        Navigation.findNavController(requireView()).popBackStack();
                                    }
                                }
                            }, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "فشل التعديل" , Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });

                }else {

                    showAndEditJobViewModel.editJobDataWithoutImg(jobId, jobName, major, jobLink
                            , jobDescription, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                        Navigation.findNavController(requireView()).popBackStack();
                                    }
                                }
                            }, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "فشل التعديل" , Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

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
        binding.showAndEditJobFragment.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.showAndEditJobFragment.setVisibility(View.VISIBLE);
    }

    public void update() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnEditJob.setEnabled(false);
        binding.btnEditJob.setClickable(false);
    }

    public void stopUpdate() {
        binding.progressBar.setVisibility(View.GONE);
        binding.btnEditJob.setEnabled(true);
        binding.btnEditJob.setClickable(true);
    }
}