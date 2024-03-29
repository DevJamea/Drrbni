package com.example.drrbni.Fragments.Auth.SignIn;

import static com.example.drrbni.Constant.PREF_STATE_AUTH;
import static com.example.drrbni.Constant.STATE_AUTH;
import static com.example.drrbni.Constant.STUDENT_TYPE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.SignInViewModel;
import com.example.drrbni.databinding.FragmentSignInBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private SignInViewModel signInViewModel;
    private SharedPreferences stateAuth;

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding
                .inflate(getLayoutInflater(), container, false);

        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        stateAuth = getActivity().getSharedPreferences(PREF_STATE_AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stateAuth.edit();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();

                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                    return;
                }

                signInViewModel.signIn(email, password, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {

                        if (value) {
                            signInViewModel.checkSignInData(email, new MyListener<Student>() {
                                @Override
                                public void onValuePosted(Student value) {

                                    if (value.getTypeUser() == STUDENT_TYPE) {

                                        if (value.getAddress() == null) {
                                            stopLoad();
                                            NavController navController = Navigation.findNavController(binding.getRoot());
                                            navController.navigate(R.id.action_loginFragment_to_signUpAddressFragment);
                                            return;
                                        } else if (value.getCollege() == null) {
                                            stopLoad();
                                            NavController navController = Navigation.findNavController(binding.getRoot());
                                            navController.navigate(R.id.action_loginFragment_to_educationInformationFragment);
                                            return;
                                        } else if (value.getWhatsApp() == null) {
                                            stopLoad();
                                            NavController navController = Navigation.findNavController(binding.getRoot());
                                            navController.navigate(R.id.action_loginFragment_to_signUpContactInformationFragment);
                                            return;
                                        } else {
                                            stopLoad();
                                            NavController navController = Navigation.findNavController(binding.getRoot());
                                            navController.navigate(R.id.action_loginFragment_to_mainFragment);
                                            editor.putBoolean(STATE_AUTH,true);
                                            editor.commit();
                                            return;
                                        }

                                    } else {
                                        // نوع المستخدم ليس طالب
                                        stopLoad();
                                        Snackbar.make(requireView(), "خطأ في البريد الألكتروني او كلمة المرور", Snackbar.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            }, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    // فشل في جلب البيانات
                                    stopLoad();
                                    Snackbar.make(requireView(), "فشل تسجيل الدخول حاول مرة اخرى", Snackbar.LENGTH_LONG).show();
                                    return;
                                }
                            });
                        }

                    }
                }, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        // لا يوجد مستخدم
                        stopLoad();
                        Snackbar.make(requireView(), "خطأ في البريد الألكتروني او كلمة المرور", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                });
            }
        });

        binding.loginTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });

        return binding.getRoot();
    }

    public void load() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setEnabled(false);
        binding.loginBtn.setClickable(false);
    }

    public void stopLoad() {
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setEnabled(true);
        binding.loginBtn.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}