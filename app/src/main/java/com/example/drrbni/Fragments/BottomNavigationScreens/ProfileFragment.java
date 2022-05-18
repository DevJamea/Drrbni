package com.example.drrbni.Fragments.BottomNavigationScreens;

import static com.example.drrbni.Constant.COLLECTION_STUDENT_PROFILES;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.NAME;
import static com.example.drrbni.Constant.SPECIALIZATION;
import static com.example.drrbni.Constant.UNIVERSITY;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseFirestore fireStore;
    private Student student;
    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        binding.addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_profileFragment_to_addJobFragment);
            }
        });

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        fireStore = FirebaseFirestore.getInstance();

       // getInfoProfile();
//        binding.studentName.setText(getInfoProfile().getEmail());
//        binding.collageName.setText(getInfoProfile().getUniversity());
//        binding.majorName.setText(getInfoProfile().getSpecialization());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*private Student getInfoProfile() {
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
    }*/

}