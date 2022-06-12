package com.example.drrbni.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.drrbni.Adapters.CategoryCompanyAdapter;
import com.example.drrbni.Models.Company;
import com.example.drrbni.ViewModels.CompanyProfileViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentCategoryItemsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CategoryItemFragment extends Fragment {

    private FragmentCategoryItemsBinding binding;
    private CategoryCompanyAdapter categoryCompanyAdapter;
    private CompanyProfileViewModel companyProfileViewModel;
    public CategoryItemFragment() {}

    public static CategoryItemFragment newInstance() {
        return new CategoryItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyProfileViewModel = new ViewModelProvider(this).get(CompanyProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryItemsBinding
                .inflate(getLayoutInflater(),container,false);

        load();
        String categoryId = CategoryItemFragmentArgs.fromBundle(requireArguments()).getCategoryId();
        String majorName = CategoryItemFragmentArgs.fromBundle(requireArguments()).getName();
        binding.title.setText(majorName);


        companyProfileViewModel.getCompaniesByMajor(majorName, new MyListener<List<Company>>() {
            @Override
            public void onValuePosted(List<Company> value) {
                stopLoad();
                if (!value.isEmpty()){
                    categoryCompanyAdapter = new CategoryCompanyAdapter(value, new MyListener<String>() {
                        @Override
                        public void onValuePosted(String value) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(CategoryItemFragmentDirections
                                    .actionCategoryItemFragmentToCompanyProfileFragment(value));
                        }
                    });
                    initRV();
                }else {
                    binding.emptyImg.setVisibility(View.VISIBLE);
                }
            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initRV() {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvCategoryItems.setLayoutManager(lm);
        binding.rvCategoryItems.setHasFixedSize(true);
        binding.rvCategoryItems.setAdapter(categoryCompanyAdapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.rvCategoryItems.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.rvCategoryItems.setVisibility(View.VISIBLE);
    }

}