package com.example.drrbni.Fragments.Auth.SignUp;

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
import com.example.drrbni.Fragments.Dialogs.AddImgFragment;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentSignUpAddImgBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpAddImgFragment extends Fragment {

    private FragmentSignUpAddImgBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private String imagePath;

    public SignUpAddImgFragment() {}

    public static SignUpAddImgFragment newInstance() {
        SignUpAddImgFragment fragment = new SignUpAddImgFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpAddImgBinding
                .inflate(getLayoutInflater(), container, false);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        String name = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getName();
        String email = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getEmail();
        String password = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getPassword();
//        String category = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getCategory();
        String governorate = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getGovernorate();
        String address = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getAddress();
        String whatsApp = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getWhatsapp();
        String university = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getUniversity();
        String specialization = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getSpecialization();

        AddImgFragment dialogAddImg = new AddImgFragment();

        binding.addImgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddImg.show(getParentFragmentManager(), null);
            }
        });

        binding.addImgBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO finish this fragment when navigate
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Student student = new Student
                                                (email,imagePath,name,specialization,university,governorate,address,whatsApp,mAuth.getUid(),1);

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
                                        navController.navigate(R.id.action_signUpAddImgFragment_to_mainFragment);
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                } else {
                    Snackbar.make(view, "تأكد من اضافة البيانات بشكل صحيح", Snackbar.LENGTH_LONG).show();
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