package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.drrbni.Fragments.Auth.SignIn.LoginFragmentDirections;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentSignUpBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    public SignUpFragment() {}

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding
                .inflate(getLayoutInflater(),container,false);

        String[] items = {"y.m" , "llk" , "ldmk"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext() , android.R.layout.simple_list_item_1, items);
        binding.spinner.setAdapter(adapter);

        binding.signUpBtnCompleteTheRegistrationProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signUpEtName.getText().toString().trim();
                String email = binding.signUpEtEmail.getText().toString().trim();
                String password = binding.signUpEtPassword.getText().toString().trim();
               // String category = binding.signUpEtCategory.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Snackbar.make(view, "أدخل الاسم", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(email)) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Snackbar.make(view, "أدخل كلمة المرور أكبر من 6 أحرف", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(email)) {
                    Snackbar.make(view, "حدد التخصص", Snackbar.LENGTH_LONG).show();
                }else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate
                            (LoginFragmentDirections.actionLoginFragmentToSignUpAddressFragment(name,email,password,email));
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