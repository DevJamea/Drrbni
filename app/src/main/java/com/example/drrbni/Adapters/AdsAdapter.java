package com.example.drrbni.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.CustomPostItemBinding;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private List<Ads> adsList;
    private Context context;
    private MyListener<String> listener;

    public AdsAdapter() {}

    public AdsAdapter(List<Ads> adsList, MyListener<String> listener) {
        this.adsList = adsList;
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
                    listener.onValuePosted(ads.getAdsId());
                }
            });
        }

        public void bind(Ads ads){
            this.ads = ads;
            binding.companyName.setText(ads.getAdsTitle());
            if (ads.getImg() == null) {
                binding.companyAvatar.setImageResource(R.drawable.company_defult_image);
            } else {
                Glide.with(context).load(ads.getImg()).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
            }
            binding.postDescription.setText(ads.getAdsDescription());
            SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
            String dateString = formatter.format(ads.getTimestamp().toDate());
            binding.postTime.setText(dateString);
        }
    }
}
