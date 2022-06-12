package com.example.drrbni.Fragments.BottomNavigationScreens;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.drrbni.Adapters.JobAdapter;
import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    private JobAdapter jobAdapter;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        profileViewModel.requestGetJobs(auth.getCurrentUser().getUid());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        load();

        profileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (getActivity() == null) return;
                if (student.getImg() == null) {
                    binding.appBarImage.setImageResource(R.drawable.defult_img_student);
                } else {
                    Glide.with(getActivity()).load(student.getImg()).placeholder(R.drawable.anim_progress).into(binding.appBarImage);
                }
                binding.studentName.setText(student.getName());
                binding.collageName.setText(student.getCollege());
                binding.studentEmail.setText(student.getEmail());
                binding.studentWhatsapp.setText(student.getWhatsApp());
                stopLoad();
            }
        });

        profileViewModel.getJobsData().observe(requireActivity(), new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                if (getActivity() == null) return;
                jobAdapter = new JobAdapter(jobs, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(R.id.action_profileFragment_to_showAndEditJobFragment);
                    }
                });
                initRV();
            }
        });

        binding.studentBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_profileFragment_to_editProfileFragment);
            }
        });

        binding.addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_profileFragment_to_addJobFragment);
            }
        });


        return binding.getRoot();
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.profileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.profileLayout.setVisibility(View.VISIBLE);
    }

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvJobs.setLayoutManager(lm);
        binding.rvJobs.setHasFixedSize(true);
        binding.rvJobs.setAdapter(jobAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}