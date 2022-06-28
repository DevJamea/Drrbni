package com.example.drrbni.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.drrbni.Constant.COMPANY_DEFAULT_IMAGE;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.HomeViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.CustomAdsItemBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

public class HomeAdapter extends FirestoreAdapter<HomeAdapter.ViewHolder> {

    private HomeViewModel homeViewModel;
    private OnJobSelectedListener mListener;

    public HomeAdapter(Query query, HomeViewModel homeViewModel, OnJobSelectedListener mListener) {
        super(query);
        this.homeViewModel = homeViewModel;
        this.mListener = mListener;
    }

    public interface OnJobSelectedListener {
        void onAdsSelected(Ads ads);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ads_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CustomAdsItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomAdsItemBinding.bind(itemView);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnJobSelectedListener listener) {
            load();

            Ads ads = snapshot.toObject(Ads.class);

            homeViewModel.getCompany(ads.getUserId(), new MyListener<Company>() {
                @Override
                public void onValuePosted(Company value) {
                    binding.companyName.setText(value.getName());
                    binding.progressBar.setVisibility(View.VISIBLE);

                    if (value.getImg() != null){
                        Glide.with(binding.companyAvatar.getContext()).load(value.getImg()).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(binding.companyAvatar);

                    }else {
                        Glide.with(binding.companyAvatar.getContext()).load(COMPANY_DEFAULT_IMAGE).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(binding.companyAvatar);

                    }

                    stopLoad();

                }
            });

            binding.postDescription.setText(ads.getAdsDescription());
            SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
            String dateString = formatter.format(ads.getTimestamp().toDate());
            binding.postTime.setText(dateString);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAdsSelected(ads);
                }
            });

        }

        public void load() {
            binding.shimmerView.setVisibility(View.VISIBLE);
            binding.shimmerView.startShimmerAnimation();
            binding.customPostItemLayout.setVisibility(View.GONE);
        }

        public void stopLoad() {
            binding.shimmerView.setVisibility(View.GONE);
            binding.shimmerView.stopShimmerAnimation();
            binding.customPostItemLayout.setVisibility(View.VISIBLE);
        }

    }

}
