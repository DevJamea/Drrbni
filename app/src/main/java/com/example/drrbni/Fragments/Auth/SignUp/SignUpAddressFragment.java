package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.databinding.FragmentSignUpAddressBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignUpAddressFragment extends Fragment {

    private FragmentSignUpAddressBinding binding;
    public SignUpAddressFragment() {}

    public static SignUpAddressFragment newInstance() {
        SignUpAddressFragment fragment = new SignUpAddressFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpAddressBinding
                .inflate(getLayoutInflater(),container,false);

        binding.signUpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = SignUpAddressFragmentArgs.fromBundle(requireArguments()).getName();
                String email = SignUpAddressFragmentArgs.fromBundle(requireArguments()).getEmail();
                String password = SignUpAddressFragmentArgs.fromBundle(requireArguments()).getPassword();
                String category = SignUpAddressFragmentArgs.fromBundle(requireArguments()).getCategory();
                String governorate = binding.signUpEtGovernorate.getText().toString().trim();
                String address = binding.signUpEtAddress.getText().toString().trim();

                if (governorate.isEmpty()) {
                    Snackbar.make(view, "حدد المحافظة", Snackbar.LENGTH_LONG).show();
                } else if (address.isEmpty()) {
                    Snackbar.make(view, "أدخل العنوان", Snackbar.LENGTH_LONG).show();
                } else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                   navController.navigate(SignUpAddressFragmentDirections.actionSignUpAddressFragmentToSignUpAddImgFragment(
                           name, email, password, category, governorate, address));
                }
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