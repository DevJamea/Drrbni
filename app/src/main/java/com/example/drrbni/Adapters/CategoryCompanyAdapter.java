package com.example.drrbni.Adapters;

import android.content.Context;
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
import com.example.drrbni.Models.Company;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.CustomCompanyItemBinding;

import java.util.List;

public class CategoryCompanyAdapter extends RecyclerView.Adapter<CategoryCompanyAdapter.CompaniesViewHolder>{

    private List<Company> companyList;
    private Context context;
    private MyListener<String> listener;

    public CategoryCompanyAdapter(List<Company> companyList, MyListener<String> listener) {
        this.companyList = companyList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public CategoryCompanyAdapter() {}

    @NonNull
    @Override
    public CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CompaniesViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_company_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompaniesViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.bind(company);
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class CompaniesViewHolder extends RecyclerView.ViewHolder{

        CustomCompanyItemBinding binding;
        Company company;

        public CompaniesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomCompanyItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(company.getUserId());
                }
            });
        }

        public void bind(Company company){
            this.company = company;
            binding.companyName.setText(company.getName());
            if (company.getImg() != null){
                binding.progressBar.setVisibility(View.VISIBLE);
                Glide.with(context).load(company.getImg()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.companyImage);
            }else {
                binding.progressBar.setVisibility(View.VISIBLE);
                Glide.with(context).load(COMPANY_DEFAULT_IMAGE).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.companyImage);
            }
            binding.companyLocation.setText(company.getGovernorate() + " _ " +company.getAddress());
            binding.companyMajor.setText(company.getCategory());
        }

    }
}
