package com.example.drrbni.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.example.drrbni.Constant.COMPANY_DEFAULT_IMAGE;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.CompanyProfileViewModel;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.CustomPostItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private List<Ads> adsList;
    private Context context;
    private CompanyProfileViewModel companyProfileViewModel;
    private String company_id;
    private MyListener<Ads> listener;

    public AdsAdapter() {}

    public AdsAdapter(List<Ads> adsList, CompanyProfileViewModel companyProfileViewModel
                   , String company_id  ,MyListener<Ads> listener) {
        this.adsList = adsList;
        this.companyProfileViewModel = companyProfileViewModel;
        this.company_id = company_id;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AdsViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_post_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        Ads ads = adsList.get(position);
        holder.bind(ads);
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    class AdsViewHolder extends RecyclerView.ViewHolder{

        CustomPostItemBinding binding;
        Ads ads;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomPostItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(ads);
                }
            });
        }

        public void bind(Ads ads){
            this.ads = ads;
            load();
            companyProfileViewModel.getCompanyImgAndName(company_id, new MyListener<Company>() {
                @Override
                public void onValuePosted(Company value) {
                    binding.companyName.setText(value.getName());
                    if (value.getImg() != null) {
                        Glide.with(context).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
                    }else {
                        Glide.with(context).load(COMPANY_DEFAULT_IMAGE).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
                    }
                    stopLoad();
                }
            });
            binding.postDescription.setText(ads.getAdsDescription());
            SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
            String dateString = formatter.format(ads.getTimestamp().toDate());
            binding.postTime.setText(dateString);
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
