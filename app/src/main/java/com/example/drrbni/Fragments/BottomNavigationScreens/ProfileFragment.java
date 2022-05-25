package com.example.drrbni.Fragments.BottomNavigationScreens;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private JobAdapter adapter;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();

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

        getInfoProfile(auth.getCurrentUser().getUid());
        getJobs(auth.getCurrentUser().getUid());


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void getInfoProfile(String uid) {
        profileViewModel.getInfoProfile(uid, new MyListener<Student>() {
            @Override
            public void onValuePosted(Student value) {
                if (value.getImg() == null){
                    binding.appBarImage.setImageResource(R.drawable.defult_img_student);
                }else {
                    binding.appBarImage.setImageURI(Uri.parse(value.getImg()));
                }
                binding.studentName.setText(value.getName());
                binding.collageName.setText(value.getCollege());
                binding.majorName.setText(value.getMajor());
                binding.studentEmail.setText(value.getEmail());
                binding.studentWhatsapp.setText(value.getWhatsApp());
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });
    }

    void getJobs(String UID){
        profileViewModel.getJobs(UID, new MyListener<List<Job>>() {
            @Override
            public void onValuePosted(List<Job> value) {
                adapter = new JobAdapter(value, new MyListener<Job>() {
                    @Override
                    public void onValuePosted(Job value) {

                    }
                });
                initRV();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });
    }

    void initRV(){
        binding.rvJobs.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.rvJobs.setLayoutManager(lm);
        binding.rvJobs.setHasFixedSize(true);
    }

}