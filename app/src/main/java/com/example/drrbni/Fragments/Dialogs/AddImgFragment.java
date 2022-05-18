package com.example.drrbni.Fragments.Dialogs;

import static com.example.drrbni.Constant.COLLECTION_STUDENT_PROFILES;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.Models.Student;
import com.example.drrbni.databinding.FragmentAddImgBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class AddImgFragment extends DialogFragment {

    private FragmentAddImgBinding binding;
    private FirebaseFirestore fireStore;
    private FirebaseAuth auth;
    private Uri imageUri;
    private String imagePath;

    public AddImgFragment() {
    }

    public static AddImgFragment newInstance() {
        AddImgFragment fragment = new AddImgFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddImgBinding
                .inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        String name = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getName();
        String email = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getEmail();
        String category = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getCategory();
        String governorate = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getGovernorate();
        String address = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getAddress();
        String whatsApp = SignUpAddImgFragmentArgs.fromBundle(requireArguments()).getWhatsapp();
         */
        String UID =auth.getUid();
        Log.e("ttt","UID: "+UID);

        ActivityResultLauncher<String> arlImg = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imageUri = result;
                    imagePath = "ProfileImage/" + UUID.randomUUID() + ".png";

                    addInformationProfile("email",imagePath,"name","","","governorate",
                            "address","whatsApp","TDrPVfMdUKPk3cq2CmOFOXSf3vE3",1);
                    dismiss();

                }

            }
        });

        ActivityResultLauncher<String> arlImgTakePicture = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imageUri = result;
                    imagePath = "ProfileImage/" + UUID.randomUUID() + ".png";

                    addInformationProfile("email",imagePath,"name","","","governorate",
                            "address","whatsApp",auth.getUid(),1);
                    dismiss();

                }

            }
        });


        binding.btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                arlImgTakePicture.launch(MediaStore.ACTION_IMAGE_CAPTURE);
            }
        });
        binding.btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arlImg.launch("image/*");
            }
        });
        binding.btnChancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void addInformationProfile(String email,String imagePath,String name,String specialization,String university,
                                       String governorate,String address,String whatsApp,String UID,int typeUser)
    {
        Student student = new Student
                (email,imagePath,name,specialization,university,governorate,address,whatsApp,UID,typeUser);

        fireStore.collection(COLLECTION_STUDENT_PROFILES).document(UID).set(student)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            StorageReference imgReF = FirebaseStorage.getInstance().getReference(imagePath);
                            imgReF.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
//                                        NavController navController = Navigation.findNavController(binding.getRoot());
//                                        navController.navigate(R.id.action_signUpAddImgFragment_to_mainFragment);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("ttt", e.getMessage());
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ttt", "onFailure : " + e.getMessage());
                    }
                });
    }

}