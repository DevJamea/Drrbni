package com.example.drrbni.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drrbni.Models.Job;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.databinding.CustomJobItemBinding;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;
    private MyListener<Job> listener;

    public JobAdapter(List<Job> jobList, MyListener<Job> listener) {
        this.jobList = jobList;
        this.listener = listener;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_item, null, false);
        JobAdapter.JobViewHolder ViewHolder = new JobAdapter.JobViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.JobViewHolder holder, int position) {
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
            this.job = job;

            if (!job.getJobName().isEmpty() && !job.getJobName().equals("")) {
                binding.jobTitle.setText(job.getJobName());
            }
            if (!job.getJobDescription().isEmpty() && !job.getJobDescription().equals("")) {
                binding.jobDescription.setText(job.getJobDescription());
            }
        }
    }

}


