package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.databinding.FragmentSignUpContactInformationBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignUpContactInformationFragment extends Fragment {

    private FragmentSignUpContactInformationBinding binding;
    public SignUpContactInformationFragment() {}

    public static SignUpContactInformationFragment newInstance() {
        SignUpContactInformationFragment fragment = new SignUpContactInformationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpContactInformationBinding
                .inflate(getLayoutInflater(),container,false);

        String name = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getName();
        String email = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getEmail();
        String password = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getPassword();
        String category = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getCategory();
        String university = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getUniversity();
        String specialization = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getSpecialization();
        String address = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getAddress();
        String governorate = SignUpContactInformationFragmentArgs.fromBundle(requireArguments()).getGovernorate();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatsApp = binding.etWhatsapp.getText().toString().trim();

                if (whatsApp.isEmpty()) {
                    Snackbar.make(view, "أدخل رقم الواتس اب", Snackbar.LENGTH_LONG).show();
                } else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(SignUpContactInformationFragmentDirections
                            .actionSignUpContactInformationFragmentToSignUpAddImgFragment
                                    (name,email,password,category,governorate,address,university,specialization,whatsApp));
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