package com.example.drrbni.Fragments.EditProfile;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Student;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.FragmentEditProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;
    private Student thisStudent;

    public EditProfileFragment() {
    }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        profileViewModel.requestGetJobs(auth.getCurrentUser().getUid());

        getImg = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            binding.profileImage.setImageURI(result);
                            image = result;
                        }
                    }
                });

        permission = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result)
                            getImg.launch("image/*");

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater(), container, false);

        load();
        getMajors();

        profileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (getActivity() == null) return;
                thisStudent = student;
                if (student.getImg() == null) {
                    binding.profileImage.setImageResource(R.drawable.defult_img_student);
                } else {
                    Glide.with(getActivity()).load(student.getImg()).placeholder(R.drawable.anim_progress).into(binding.profileImage);
                }
                binding.editProfileEtName.setText(student.getName());
                binding.editProfileEtEmail.setText(student.getEmail());
                stopLoad();
            }
        });

        profileViewModel.getCategoriesName(new MyListener<List<String>>() {
            @Override
            public void onValuePosted(List<String> value) {
                int position = 0;
                checkCategoryName(position, thisStudent);
                stopLoad();
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });


        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editProfileEtName.getText().toString().trim();
                String email = binding.editProfileEtEmail.getText().toString().trim();
                String major = binding.editProfileCategories.getSelectedItem().toString();

                if (TextUtils.isEmpty(name))
                    name = thisStudent.getName();
                else if (TextUtils.isEmpty(email))
                    email = thisStudent.getEmail();
                else if (binding.editProfileCategories.getSelectedItemPosition() < 1)
                    major = thisStudent.getMajor();

                if (image == null) {
                    profileViewModel.editProfile(name, email, major, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            if (value) {
                                Snackbar.make(view, "تم التعديل بنجاح", Snackbar.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
                } else {
                    profileViewModel.editProfileDataWithImg(image, name, email, major, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            if (value) {
                                Snackbar.make(view, "تم التعديل بنجاح", Snackbar.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            }
                        }
                    }, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            if (value) {
                                Snackbar.make(view, "فشل التعديل", Snackbar.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
                }
            }
        });

        binding.tvEditContactInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_editContactInformationFragment);
            }
        });
        binding.tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_editAddressFragment);
            }
        });
        binding.tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordBottomSheetFragment bottomSheet =
                        ChangePasswordBottomSheetFragment.newInstance();
                bottomSheet.show(getFragmentManager(), "tag");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.editProfileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileLayout.setVisibility(View.VISIBLE);
    }

    private void getMajors() {
        profileViewModel.getMajors(new MyListener<List<String>>() {
            @Override
            public void onValuePosted(List<String> value) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, value);
                binding.editProfileCategories.setAdapter(adapter);
                stopLoad();
            }
        });
    }

    private void checkCategoryName(int position, Student student) {
        if (student.getMajor().equals("أختر التخصص")) {
            position = 0;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("التسويق")) {
            position = 1;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("تكنولوجيا المعلومات")) {
            position = 2;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("الهندسة")) {
            position = 3;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("البلديات")) {
            position = 4;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("التصميم والديكور")) {
            position = 5;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("المحاسبة")) {
            position = 6;
            binding.editProfileCategories.setSelection(position);
        } else if (student.getMajor().equals("الصحافة والاعلام")) {
            position = 7;
            binding.editProfileCategories.setSelection(position);
        }
    }

}