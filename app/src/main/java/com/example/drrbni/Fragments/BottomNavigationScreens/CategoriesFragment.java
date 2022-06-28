package com.example.drrbni.Fragments.BottomNavigationScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drrbni.Adapters.CategoryAdapter;
import com.example.drrbni.Models.Category;
import com.example.drrbni.ViewModels.CategoryViewHolder;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentCategoriesBinding;

import java.util.List;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private CategoryAdapter adapter;
    private CategoryViewHolder categoryViewHolder;

    public CategoriesFragment() {}

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewHolder = new ViewModelProvider(this).get(CategoryViewHolder.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding
                .inflate(inflater, container, false);

        load();

        categoryViewHolder.getCategories(new MyListener<List<Category>>() {
            @Override
            public void onValuePosted(List<Category> values) {
                if (getActivity() == null) return;

                adapter = new CategoryAdapter(values, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(CategoriesFragmentDirections.
                                actionCategoriesFragmentToCategoryItemFragment(value));
                    }
                });
                stopLoad();
                initRV();
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
        binding.rvCategories.setLayoutManager(lm);
        binding.rvCategories.setHasFixedSize(true);
        binding.rvCategories.setAdapter(adapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.linear.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.linear.setVisibility(View.VISIBLE);
    }

}