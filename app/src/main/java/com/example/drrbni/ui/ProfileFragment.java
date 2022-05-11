package com.example.drrbni.ui;

import static com.example.drrbni.Constant.COLLECTION_STUDENT_PROFILES;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.NAME;
import static com.example.drrbni.Constant.SPECIALIZATION;
import static com.example.drrbni.Constant.UNIVERSITY;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbni.Models.Student;
import com.example.drrbni.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseFirestore fireStore;
    private Student student;


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        fireStore = FirebaseFirestore.getInstance();

        getInfoProfile();
//        binding.studentName.setText(getInfoProfile().getEmail());
//        binding.collageName.setText(getInfoProfile().getUniversity());
//        binding.majorName.setText(getInfoProfile().getSpecialization());

        return binding.getRoot();
    }

    private Student getInfoProfile() {
        fireStore.collection(COLLECTION_STUDENT_PROFILES)
                .document(currentUser.getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("ttt", "error" + error);
                            return;
                        }

                        student = new Student();
                        if (value != null) {
                            student.setEmail(value.getString(EMAIL));
                            student.setName(value.getString(NAME));
                            student.setUniversity(value.getString(UNIVERSITY));
                            student.setSpecialization(value.getString(SPECIALIZATION));

                            Log.d("ttt", "email: " + value.getString(EMAIL));
                            Log.d("ttt", "Specialization: " + student.getSpecialization());
                            Log.d("ttt", "University: " + student.getUniversity());
                        } else {
                            Log.e("ttt", "value is null");
                        }
                    }
                });
        return student;
    }

}