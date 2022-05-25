package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.R;
import com.example.drrbni.databinding.FragmentChangePasswordBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChangePasswordBottomSheetFragment extends BottomSheetDialogFragment {


    private FragmentChangePasswordBottomSheetBinding binding;
    public ChangePasswordBottomSheetFragment() {}

    public static ChangePasswordBottomSheetFragment newInstance() {
        return new ChangePasswordBottomSheetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.bottomSheet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBottomSheetBinding
                .inflate(getLayoutInflater(),container,false);

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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