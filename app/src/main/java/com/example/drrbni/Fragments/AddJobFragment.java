package com.example.drrbni.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentAddJobBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AddJobFragment extends Fragment {

    private FragmentAddJobBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;

    public AddJobFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

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
                }
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddJobBinding
                .inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();

        binding.imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobName = binding.etJobName.getText().toString().trim();
                String major = binding.spMajor.getSelectedItem().toString();
                String jobLink = binding.etJobLink.getText().toString().trim();
                String jobDescription = binding.etDescription.getText().toString().trim();

                if (TextUtils.isEmpty(jobName)) {
                    Snackbar.make(view, "أدخل اسم العمل", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (binding.spMajor.getSelectedItemPosition() < 1) {
                    Snackbar.make(view, "أدخل التخصص", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(jobDescription)) {
                    Snackbar.make(view, "أدخل وصف العمل", Snackbar.LENGTH_LONG).show();
                    return;
                }

                profileViewModel.storeJobData(auth.getCurrentUser().getUid(), image, jobName, major, jobLink, jobDescription, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        //TODO:: finish this fragment
                    }
                });
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