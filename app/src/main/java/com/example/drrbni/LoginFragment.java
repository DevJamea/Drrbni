package com.example.drrbni;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class LoginFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    public LoginFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = binding.loginEmail.getText().toString().trim();
                String mPassword = binding.loginPassword.getText().toString().trim();

                if (mEmail.isEmpty()) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();

                } else if (mPassword.isEmpty()) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(mEmail, mPassword).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, String> dataProfileUser = new HashMap<>();
                                dataProfileUser.put("Email", mEmail);
                                dataProfileUser.put("Name", "");
                                dataProfileUser.put("Img", "");
                                dataProfileUser.put("University", "");
                                dataProfileUser.put("Specialization", "");
                                dataProfileUser.put("UserId", mAuth.getUid());

                                fireStore.collection("StudentProfiles").document(mAuth.getUid()).set(dataProfileUser).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                navController.navigate(R.id.action_login_to_homeActivity);
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

        return binding.getRoot();
    }
}
