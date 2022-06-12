package com.example.drrbni.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
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
    public CategoryCompanyAdapter.CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryCompanyAdapter.CompaniesViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_company_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCompanyAdapter.CompaniesViewHolder holder, int position) {
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
            if (company.getImg() == null) {
                //todo: out of memory error
//                binding.companyImg.setImageResource(R.drawable.company_defult_image);
            } else {
                Glide.with(context).load(company.getImg()).placeholder(R.drawable.anim_progress).into(binding.companyImg);
            }
            binding.companyLocation.setText(company.getAddress());
            binding.companyMajor.setText(company.getCategory());
        }

    }
}
