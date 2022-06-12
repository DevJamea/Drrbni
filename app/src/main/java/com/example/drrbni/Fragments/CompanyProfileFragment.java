package com.example.drrbni.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.drrbni.Adapters.AdsAdapter;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.CompanyProfileViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.FragmentCompanyProfileBinding;

import java.util.List;

public class CompanyProfileFragment extends Fragment {

    private FragmentCompanyProfileBinding binding;
    private AdsAdapter adsAdapter;
    private CompanyProfileViewModel companyProfileViewModel;
    public CompanyProfileFragment() {}

    public static CompanyProfileFragment newInstance() {
        return new CompanyProfileFragment();
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
        String userId = CompanyProfileFragmentArgs.fromBundle(requireArguments()).getUserId();

        companyProfileViewModel.getInfoCompanyByUID(userId, new MyListener<Company>() {
            @Override
            public void onValuePosted(Company value) {
                stopLoad();
                if (value.getImg() == null) {
                    binding.appBarImage.setImageResource(R.drawable.defult_img_student);
                }else {
                    Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.appBarImage);
                }
                binding.companyName.setText(value.getName());
                binding.companyLocation.setText(value.getAddress());
                binding.companyMajor.setText(value.getCategory());
                try {
                    binding.whatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "https://api.whatsapp.com/send?phone="+value.getWhatsApp();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    });
                    binding.Gmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requireActivity().startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+value.getEmail())));
                        }
                    });
                }catch (Exception e){
                    Log.e("ttt",e.getMessage());
                }

            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });

        companyProfileViewModel.getAdsByUid(userId, new MyListener<List<Ads>>() {
            @Override
            public void onValuePosted(List<Ads> value) {
                stopLoad();
                adsAdapter = new AdsAdapter(value, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {

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

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvAds.setLayoutManager(lm);
        binding.rvAds.setHasFixedSize(true);
        binding.rvAds.setAdapter(adsAdapter);
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

}