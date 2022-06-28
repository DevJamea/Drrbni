package com.example.drrbni.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbni.Fragments.BottomNavigationScreens.ProfileFragmentDirections;
import com.example.drrbni.Models.Job;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.ProfileViewModel;
import com.example.drrbni.databinding.CustomJobItemBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;
    private Context context;
    private MyListener<String> listener;
    private ProfileViewModel profileViewModel;

    public JobAdapter() {}

    public JobAdapter(List<Job> jobList, ProfileViewModel profileViewModel , MyListener<String> listener) {
        this.jobList = jobList;
        this.profileViewModel = profileViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new JobViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.bind(job);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

        CustomJobItemBinding binding;
        Job job;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomJobItemBinding.bind(itemView);
        }

        public void bind(Job job) {
            load();
            this.job = job;
            binding.jobTitle.setText(job.getJobName());
            binding.jobDescription.setText(job.getJobDescription());
            binding.progressBar.setVisibility(View.VISIBLE);
            Glide.with(context).load(job.getImg()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    binding.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(binding.jobImage);

            binding.jobMenu.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context , binding.jobMenu);
                    popup.inflate(R.menu.job_menu);
                    popup.setForceShowIcon(true);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.jobMenuEdit:
                                    NavController navController = Navigation.findNavController(binding.getRoot());
                                    navController.navigate(ProfileFragmentDirections
                                            .actionProfileFragmentToShowAndEditJobFragment(job.getJobId()));
                                    return true;
                                case R.id.jobMenuDelete:
                                    profileViewModel.deleteJob(job.getJobId(), new MyListener<Boolean>() {
                                        @Override
                                        public void onValuePosted(Boolean value) {
                                            Snackbar.make(binding.getRoot() , "تم الحذف" , Snackbar.LENGTH_LONG).show();
                                        }
                                    }, new MyListener<Boolean>() {
                                        @Override
                                        public void onValuePosted(Boolean value) {
                                            Snackbar.make(binding.getRoot() , "فشل الحذف" , Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                }
            });

            stopLoad();
        }

        public void load() {
            binding.shimmerView.setVisibility(View.VISIBLE);
            binding.shimmerView.startShimmerAnimation();
            binding.customJobsLayout.setVisibility(View.GONE);
        }

        public void stopLoad() {
            binding.shimmerView.setVisibility(View.GONE);
            binding.shimmerView.stopShimmerAnimation();
            binding.customJobsLayout.setVisibility(View.VISIBLE);
        }
    }

}


