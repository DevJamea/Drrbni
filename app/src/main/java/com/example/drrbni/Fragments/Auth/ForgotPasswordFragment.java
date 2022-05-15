package com.example.drrbni.Fragments.Auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private FirebaseAuth mAuth;
    public ForgotPasswordFragment() {}

    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater(), container, false);

        mAuth = FirebaseAuth.getInstance();

        binding.forgotPasswordBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.forgotPasswordEtEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    Snackbar.make(view, "أدخل البريد الالكتروني", Snackbar.LENGTH_LONG).show();
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(view, "تم إرسال رابط إعادة تعيين كلمة المرور على بريدك الإلكتروني", Snackbar.LENGTH_LONG).show();
                                NavController navController = Navigation.findNavController(binding.getRoot());
                                navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
                            } else {
                                Snackbar.make(view, task.getException().getMessage() , Snackbar.LENGTH_LONG).show();
                            }
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    });
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