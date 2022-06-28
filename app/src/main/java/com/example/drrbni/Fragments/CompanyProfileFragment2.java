package com.example.drrbni.Fragments;

import static com.example.drrbni.Constant.COMPANY_DEFAULT_IMAGE_PROFILE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbni.Adapters.AdsAdapter;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.ViewModels.CompanyProfileViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentCompanyProfileBinding;

import java.util.List;

public class CompanyProfileFragment2 extends Fragment {

    private FragmentCompanyProfileBinding binding;
    private AdsAdapter adsAdapter;
    private CompanyProfileViewModel companyProfileViewModel;
    public CompanyProfileFragment2() {}

    public static CompanyProfileFragment2 newInstance() {
        return new CompanyProfileFragment2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyProfileViewModel = new ViewModelProvider(this).get(CompanyProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyProfileBinding
                .inflate(getLayoutInflater(),container,false);
        load();


        String userId = getArguments().getString("companyId").trim();

        companyProfileViewModel.getInfoCompanyByUID(userId, new MyListener<Company>() {
            @Override
            public void onValuePosted(Company value) {
                if (getActivity() == null)
                    return;

                if (value.getImg() != null){
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Glide.with(requireActivity()).load(value.getImg()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(binding.appBarImage);
                }else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Glide.with(requireActivity()).load(COMPANY_DEFAULT_IMAGE_PROFILE).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(binding.appBarImage);
                }
                binding.companyName.setText(value.getName());
                binding.companyEmail.setText(value.getEmail());
                binding.companyWhatsapp.setText(value.getWhatsApp());
                if (value.isVerified())
                    binding.verified.setVisibility(View.VISIBLE);
                binding.address.setText(value.getGovernorate() + " _ " +value.getAddress());

                stopLoad();
            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });

        companyProfileViewModel.getAdsByUid(userId, new MyListener<List<Ads>>() {
            @Override
            public void onValuePosted(List<Ads> value) {
                if (getActivity() == null) return;
                adsAdapter = new AdsAdapter(value, companyProfileViewModel , userId
                        , new MyListener<Ads>() {
                    @Override
                    public void onValuePosted(Ads value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(CompanyProfileFragment2Directions
                        .actionCompanyProfileFragment2ToShowPostFragment2(value));
                    }
                });
                initRV();
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

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.profileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.profileLayout.setVisibility(View.VISIBLE);
    }

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvAds.setLayoutManager(lm);
        binding.rvAds.setHasFixedSize(true);
        binding.rvAds.setAdapter(adsAdapter);
    }

}