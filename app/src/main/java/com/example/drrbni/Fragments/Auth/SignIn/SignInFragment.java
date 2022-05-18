package com.example.drrbni.Fragments.Auth.SignIn;

import static com.example.drrbni.Constant.COLLECTION_STUDENT_PROFILES;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    public SignInFragment() {}

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding
                .inflate(getLayoutInflater(),container,false);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = binding.loginEmail.getText().toString().trim();
                String mPassword = binding.loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(mPassword)) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(mEmail, mPassword).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Student student = new Student(mEmail,"","","","",mAuth.getUid());

                                fireStore.collection(COLLECTION_STUDENT_PROFILES).document(mAuth.getUid()).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("ttt", "onFailure : " + e.getMessage());
                                    }
                                });

                                NavController navController = Navigation.findNavController(binding.getRoot());
                                navController.navigate(R.id.action_loginFragment_to_mainFragment);
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            } else {
                                binding.progressBar.setVisibility(View.VISIBLE);
                                Snackbar.make(view, "الحساب غير موجود", Snackbar.LENGTH_LONG).show();
                            }
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}