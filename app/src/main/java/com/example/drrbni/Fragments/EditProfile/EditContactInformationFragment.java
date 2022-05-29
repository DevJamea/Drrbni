package com.example.drrbni.Fragments.EditProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentEditContactInformationBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EditContactInformationFragment extends Fragment {

    private FragmentEditContactInformationBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    public EditContactInformationFragment() {}

    public static EditContactInformationFragment newInstance() {
        return new EditContactInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        profileViewModel.requestGetJobs(auth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditContactInformationBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        profileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (getActivity() == null) return;
                binding.etWhatsapp.setText(student.getWhatsApp());
                stopLoad();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatsapp = binding.etWhatsapp.getText().toString().trim();
                profileViewModel.editProfileContactInformation(whatsapp, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        //TODO finish this fragment
                    }
                });
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
        binding.editProfileContactInformationLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileContactInformationLayout.setVisibility(View.VISIBLE);
    }

}