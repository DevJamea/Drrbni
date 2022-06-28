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
import com.example.drrbni.ViewModels.AddressViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentSignUpAddressBinding;
import com.google.android.material.snackbar.Snackbar;


public class SignUpAddressFragment extends Fragment {

    private FragmentSignUpAddressBinding binding;
    private AddressViewModel addressViewModel;

    public SignUpAddressFragment() {}

    public static SignUpAddressFragment newInstance() {
        return new SignUpAddressFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpAddressBinding
                .inflate(getLayoutInflater(),container,false);


        binding.signUpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
                String governorate = binding.signUpGovernorate.getSelectedItem().toString();;
                String address = binding.signUpEtAddress.getText().toString().trim();

                if (binding.signUpGovernorate.getSelectedItemPosition() < 1) {
                    Snackbar.make(view, "حدد المحافظة", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(address)) {
                    Snackbar.make(view, "أدخل العنوان", Snackbar.LENGTH_LONG).show();
                    return;
                }

                load();
                addressViewModel.storeData(governorate, address, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value){
                            stopLoad();
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_signUpAddressFragment_to_educationInformationFragment);
                        }
                    }
                });


            }
        });

        return binding.getRoot();
    }

    public void load(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signUpBtnNext.setEnabled(false);
        binding.signUpBtnNext.setClickable(false);
    }

    public void stopLoad(){
        binding.progressBar.setVisibility(View.GONE);
        binding.signUpBtnNext.setEnabled(true);
        binding.signUpBtnNext.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}