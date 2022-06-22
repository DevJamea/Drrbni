package com.example.drrbni.Fragments.BottomNavigationScreens;

import static com.example.drrbni.Constant.COLLECTION_ADS;
import static com.example.drrbni.Constant.MAJOR;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.drrbni.Adapters.HomeAdapter;
import com.example.drrbni.Fragments.Dialogs.FilterDialogFragment;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Filters;
import com.example.drrbni.ViewModels.HomeViewModel;
import com.example.drrbni.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment implements FilterDialogFragment.FilterListener
        , HomeAdapter.OnJobSelectedListener{

    private FragmentHomeBinding binding;
    private FilterDialogFragment filter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private HomeAdapter homeAdapter;
    private HomeViewModel homeViewModel;
    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding
                .inflate(inflater, container, false);

        load();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        FirebaseFirestore.setLoggingEnabled(true);
        initFirestore();
        initRecyclerView();

        filter = new FilterDialogFragment(this);

        binding.filterBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter.show(getParentFragmentManager() , FilterDialogFragment.TAG);
            }
        });

        binding.buttonClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearFilter();
                binding.buttonClearFilter.setVisibility(View.GONE);
            }
        });


        return binding.getRoot();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection(COLLECTION_ADS);
    }

    private void initRecyclerView() {
        homeAdapter = new HomeAdapter(mQuery,homeViewModel,this) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                stopLoad();
                if (getItemCount() == 0) {
                    binding.rvPostItems.setVisibility(View.GONE);
                    binding.noData.setVisibility(View.VISIBLE);
                } else {
                    binding.rvPostItems.setVisibility(View.VISIBLE);
                    binding.noData.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                Log.d("sssssssss" , e.toString());
            }

        };

        binding.rvPostItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPostItems.setHasFixedSize(true);
        binding.rvPostItems.setAdapter(homeAdapter);

        homeAdapter.startListening();
    }

    @Override
    public void onFilter(Filters filters) {

        Query query = mFirestore.collection(COLLECTION_ADS);

        // Employment
        if (filters.hasEmployment()) {
            query = query.whereEqualTo(MAJOR, filters.getEmployment());
            binding.textCurrentSortBy.setVisibility(View.VISIBLE);
            binding.textCurrentSortBy.setText(filters.getEmployment());
            binding.buttonClearFilter.setVisibility(View.VISIBLE);
        }
        mQuery = query;
        homeAdapter.setQuery(query);

    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.homeLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.homeLayout.setVisibility(View.VISIBLE);
    }

    public void onClearFilter(){
        filter.resetFilters();
        mQuery = mFirestore.collection(COLLECTION_ADS);
        homeAdapter.setQuery(mQuery);
        binding.textCurrentSortBy.setVisibility(View.GONE);
    }

    @Override
    public void onAdsSelected(Ads ads) {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(HomeFragmentDirections
                .actionHomeFragmentToShowPostFragment(ads));
    }
}