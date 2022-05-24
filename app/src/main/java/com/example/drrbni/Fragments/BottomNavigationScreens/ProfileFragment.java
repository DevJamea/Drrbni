package com.example.drrbni.Fragments.BottomNavigationScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
//        binding.collageName.setText(getInfoProfile().getCollege());
//        binding.majorName.setText(getInfoProfile().getMajor());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*private Student getInfoProfile() {
        fireStore.collection(COLLECTION_USERS_PROFILES)
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
                            student.setCollege(value.getString(UNIVERSITY));
                            student.setMajor(value.getString(MAJOR));

                            Log.d("ttt", "email: " + value.getString(EMAIL));
                            Log.d("ttt", "Specialization: " + student.getMajor());
                            Log.d("ttt", "University: " + student.getCollege());
                        } else {
                            Log.e("ttt", "value is null");
                        }
                    }
                });
        return student;
    }*/

}