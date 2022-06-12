package com.example.drrbni.Fragments.EditProfile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Student;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentEditAddressBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EditAddressFragment extends Fragment {

    private FragmentEditAddressBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    public EditAddressFragment() {}

    public static EditAddressFragment newInstance() {
        return new EditAddressFragment();
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
        binding = FragmentEditAddressBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        profileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (getActivity() == null) return;
                int position=0;
                checkGovernmentSpinner(position, student);

                binding.etAddress.setText(student.getAddress());
                stopLoad();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String governorate = (String) binding.spGovernorate.getSelectedItem();
                String address = binding.etAddress.getText().toString().trim();
                profileViewModel.editAddressData(governorate,address, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void checkGovernmentSpinner(int position,Student student) {
        if (student.getGovernorate().equals("أختر المحافظة")){
            position = 0;
            binding.spGovernorate.setSelection(position);
        } else if (student.getGovernorate().equals("شمال غزة")){
            position = 1;
            binding.spGovernorate.setSelection(position);
        } else if (student.getGovernorate().equals("غزة")){
            position = 2;
            binding.spGovernorate.setSelection(position);
        } else if (student.getGovernorate().equals("الوسطى")){
            position = 3;
            binding.spGovernorate.setSelection(position);
        } else if (student.getGovernorate().equals("خانيونس")){
            position = 4;
            binding.spGovernorate.setSelection(position);
        } else if (student.getGovernorate().equals("رفح")){
            position = 5;
            binding.spGovernorate.setSelection(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.editProfileAddressLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileAddressLayout.setVisibility(View.VISIBLE);
    }

}