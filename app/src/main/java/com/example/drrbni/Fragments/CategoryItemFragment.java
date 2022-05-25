package com.example.drrbni.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.drrbni.databinding.FragmentCategoryItemsBinding;

public class CategoryItemFragment extends Fragment {

    private FragmentCategoryItemsBinding binding;
    public CategoryItemFragment() {}

    public static CategoryItemFragment newInstance() {
        return new CategoryItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryItemsBinding
                .inflate(getLayoutInflater(),container,false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}