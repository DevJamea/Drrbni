package com.example.drrbni.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Notification.FcmNotificationsSender;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.Repository;
import com.example.drrbni.ViewModels.ShowPostFragmentViewModel;
import com.example.drrbni.databinding.FragmentShowPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShowPostFragment extends Fragment {

    private FragmentShowPostBinding binding;
    private Ads ads;
    private boolean showBtn;
    private ShowPostFragmentViewModel showPostFragmentViewModel;
    private FirebaseUser firebaseUser;
    private FcmNotificationsSender notificationsSender;
    public ShowPostFragment() {}

    public static ShowPostFragment newInstance() {
        return new ShowPostFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ads = (Ads) getArguments().getSerializable("adsObject");
        showBtn = getArguments().getBoolean("showBtn");
        showPostFragmentViewModel = new ViewModelProvider(this).get(ShowPostFragmentViewModel.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPostBinding
                .inflate(getLayoutInflater(),container,false);

        if (showBtn)
            binding.btnNavigate.setVisibility(View.GONE);

        if (ads.getRequests().contains(firebaseUser.getUid()))
            binding.btnRequest.setVisibility(View.GONE);

        binding.adsTitle.setText(ads.getAdsTitle());
        binding.adsMajor.setText(ads.getMajor());
        binding.adsDescription.setText(ads.getAdsDescription());
        binding.adsRequirements.setText(ads.getAdsRequirements());
        binding.progressBar.setVisibility(View.VISIBLE);
        Glide.with(requireContext()).load(ads.getImg()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(binding.adsImage);

        binding.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(ShowPostFragmentDirections
                .actionShowPostFragmentToCompanyProfileFragment22(ads.getUserId()));
            }
        });

        binding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void sendNotification(){

        showPostFragmentViewModel.getNameByUid(firebaseUser.getUid(), new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

                String senderName = value;
                showPostFragmentViewModel.getTokenByCompanyId(ads.getUserId(), new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        notificationsSender = new FcmNotificationsSender(value , senderName ,ads.getAdsId() ,requireActivity());
                        notificationsSender.SendNotifications();
                    }
                });

            }
        });

        showPostFragmentViewModel.addToRequestArray(ads.getAdsId());
        binding.btnRequest.setVisibility(View.GONE);

    }
}