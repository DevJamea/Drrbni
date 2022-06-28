package com.example.drrbni.Fragments.EditProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.drrbni.Models.Student;
import com.example.drrbni.ViewModels.EditContactInformationViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentEditContactInformationBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditContactInformationFragment extends Fragment {

    private FragmentEditContactInformationBinding binding;
    private FirebaseAuth auth;
    private EditContactInformationViewModel editContactInformationViewModel;
    public EditContactInformationFragment() {}

    public static EditContactInformationFragment newInstance() {
        return new EditContactInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        editContactInformationViewModel = new ViewModelProvider(this).get(EditContactInformationViewModel.class);
        editContactInformationViewModel.requestProfileInfo(auth.getCurrentUser().getUid());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditContactInformationBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        editContactInformationViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
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
                update();
                String whatsapp = binding.etWhatsapp.getText().toString().trim();
                editContactInformationViewModel.editProfileContactInformation(whatsapp, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value) {
                            stopUpdate();
                            Snackbar.make(view, "تم التعديل بنجاح", Snackbar.LENGTH_LONG).show();
                            Navigation.findNavController(binding.getRoot()).popBackStack();
                        }else {
                            stopUpdate();
                            Snackbar.make(view, "فشل التعديل", Snackbar.LENGTH_LONG).show();
                        }
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
    public void update() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnOk.setEnabled(false);
        binding.btnOk.setClickable(false);
    }

    public void stopUpdate() {
        binding.progressBar.setVisibility(View.GONE);
        binding.btnOk.setEnabled(true);
        binding.btnOk.setClickable(true);
    }
}