package com.example.drrbni.Fragments.Auth.SignUp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.ContactInformationViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentSignUpContactInformationBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignUpContactInformationFragment extends Fragment {

    private FragmentSignUpContactInformationBinding binding;
    private ContactInformationViewModel myViewModel;
    
    public SignUpContactInformationFragment() {}

    public static SignUpContactInformationFragment newInstance() {
        return new SignUpContactInformationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myViewModel = new ViewModelProvider(this).get(ContactInformationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpContactInformationBinding
                .inflate(getLayoutInflater(),container,false);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatsApp = binding.etWhatsapp.getText().toString().trim();

                if (TextUtils.isEmpty(whatsApp)){
                    Snackbar.make(view, "أدخل رقم الواتس آب ", Snackbar.LENGTH_LONG).show();
                    return;
                }

                load();

                myViewModel.storeData(whatsApp, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value){
                            stopLoad();
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_signUpContactInformationFragment_to_signUpAddImgFragment);
                        }
                    }
                });

            }
        });

        return binding.getRoot();
    }

    public void load(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnNext.setEnabled(false);
        binding.btnNext.setClickable(false);
    }

    public void stopLoad(){
        binding.progressBar.setVisibility(View.GONE);
        binding.btnNext.setEnabled(true);
        binding.btnNext.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}