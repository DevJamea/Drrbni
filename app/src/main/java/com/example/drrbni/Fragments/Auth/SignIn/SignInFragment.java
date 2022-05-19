package com.example.drrbni.Fragments.Auth.SignIn;

import static com.example.drrbni.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbni.Constant.TYPE_USER;

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

import com.example.drrbni.R;
import com.example.drrbni.ViewModel.MyViewModel;
import com.example.drrbni.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private MyViewModel myViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding
                .inflate(getLayoutInflater(), container, false);



        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(password)) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);

                    /*
                    myViewModel.init(COLLECTION_USERS_PROFILES,EMAIL,email);
                    myViewModel.getData().observe(requireActivity(), new Observer<List<QueryDocumentSnapshot>>() {
                        @Override
                        public void onChanged(List<QueryDocumentSnapshot> queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                                Log.d("ttt","snapshot: "+snapshot.toObject(Student.class).getEmail());
                            }
                            Log.d("ttt","---------------------------------");
                        }
                    });
                     */


                    fireStore.collection(COLLECTION_USERS_PROFILES).whereEqualTo(TYPE_USER, 1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                Log.e("ttt","List User: "+task.getResult().getDocuments());
                                //TODO فحص اذا الايميل الراجع يساوي الايميل المدخل
                                SignIn(email,password,view);
                            }else {
                                Snackbar.make(view, "ليس لديك الصلاحية في الوصول الى هذا الحساب..تأكد من ادخال البيانات بشكل صحيح", Snackbar.LENGTH_LONG).show();
                            }
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

    private void SignIn(String email,String password,View view){
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_loginFragment_to_mainFragment);
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            binding.progressBar.setVisibility(View.VISIBLE);
                            Snackbar.make(view, task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}