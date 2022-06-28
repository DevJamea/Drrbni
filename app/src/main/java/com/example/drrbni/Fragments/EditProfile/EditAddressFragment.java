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
import com.example.drrbni.SpinnerPosition;
import com.example.drrbni.ViewModels.EditAddressViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentEditAddressBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditAddressFragment extends Fragment {

    private FragmentEditAddressBinding binding;
    private FirebaseAuth auth;
    private EditAddressViewModel editAddressViewModel;
    private SpinnerPosition spinnerPosition;

    public EditAddressFragment() {}

    public static EditAddressFragment newInstance() {
        return new EditAddressFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAddressBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        auth = FirebaseAuth.getInstance();
        editAddressViewModel = new ViewModelProvider(this).get(EditAddressViewModel.class);
        editAddressViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        spinnerPosition = SpinnerPosition.getInstance();

        editAddressViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (getActivity() == null) return;
                binding.spGovernorate.setSelection(spinnerPosition.getGovernoratePosition(student.getGovernorate()));
                binding.etAddress.setText(student.getAddress());
                stopLoad();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String governorate = (String) binding.spGovernorate.getSelectedItem();
                String address = binding.etAddress.getText().toString().trim();
                editAddressViewModel.editAddressData(governorate,address, new MyListener<Boolean>() {
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
        binding.editProfileAddressLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileAddressLayout.setVisibility(View.VISIBLE);
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