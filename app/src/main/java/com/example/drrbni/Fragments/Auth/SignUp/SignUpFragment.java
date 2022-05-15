package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Fragments.Auth.SignIn.LoginFragmentDirections;
import com.example.drrbni.databinding.FragmentSignUpBinding;
import com.google.android.material.snackbar.Snackbar;

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

        binding.signUpBtnCompleteTheRegistrationProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signUpEtName.getText().toString().trim();
                String email = binding.signUpEtEmail.getText().toString().trim();
                String password = binding.signUpEtPassword.getText().toString().trim();
                String category = binding.signUpEtCategory.getText().toString().trim();

                if (name.isEmpty()) {
                    Snackbar.make(view, "أدخل الاسم", Snackbar.LENGTH_LONG).show();
                } else if (email.isEmpty()) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Snackbar.make(view, "أدخل كلمة المرور أكبر من 6 أحرف", Snackbar.LENGTH_LONG).show();
                } else if (category.isEmpty()) {
                    Snackbar.make(view, "حدد التخصص", Snackbar.LENGTH_LONG).show();
                }else {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate
                            (LoginFragmentDirections.actionLoginFragmentToSignUpAddressFragment(name,email,password,category));
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