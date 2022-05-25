package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    public EditProfileFragment() {}

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater(),container,false);

        binding.tvEditContactInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_signUpContactInformationFragment2);
            }
        });

        binding.tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_signUpAddressFragment);
            }
        });

        binding.tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordBottomSheetFragment bottomSheet =
                        ChangePasswordBottomSheetFragment.newInstance();
                bottomSheet.show(getFragmentManager() , "tag");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}