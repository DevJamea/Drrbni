package com.example.drrbni.Fragments.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.SignInViewModel;
import com.example.drrbni.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private SignInViewModel viewModel;

    public ForgotPasswordFragment() {
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater(), container, false);


        binding.forgotPasswordBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.forgotPasswordEtEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Snackbar.make(view, "أدخل البريد الالكتروني", Snackbar.LENGTH_LONG).show();
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    viewModel.resetPassword(email, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            Snackbar.make(view, "تم إرسال رابط إعادة تعيين كلمة المرور على بريدك الإلكتروني", Snackbar.LENGTH_LONG).show();
                            Navigation.findNavController(binding.getRoot()).popBackStack();
                        }
                    }, new MyListener<String>() {
                        @Override
                        public void onValuePosted(String value) {
                            // todo
                            Snackbar.make(view, value, Snackbar.LENGTH_LONG).show();
                        }
                    });
                    binding.progressBar.setVisibility(View.GONE);
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