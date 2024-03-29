package com.example.drrbni.Fragments.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drrbni.databinding.FragmentNotificationDialogBinding;

public class NotificationDialogFragment extends Fragment {

    private FragmentNotificationDialogBinding binding;
    public NotificationDialogFragment() {}

    public static NotificationDialogFragment newInstance() {
        return new NotificationDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationDialogBinding
                .inflate(getLayoutInflater(),container,false);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}