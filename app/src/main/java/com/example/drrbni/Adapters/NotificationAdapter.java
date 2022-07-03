package com.example.drrbni.Adapters;

import static com.example.drrbni.Constant.COMPANY_DEFAULT_IMAGE;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbni.Models.Company;
import com.example.drrbni.Models.Notification;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.NotificationViewModel;
import com.example.drrbni.databinding.CustomNotificationItemBinding;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private Context context;
    private NotificationViewModel notificationViewModel;
    private String uid;

    public NotificationAdapter(List<Notification> notificationList, NotificationViewModel notificationViewModel, String uid) {
        this.notificationList = notificationList;
        this.notificationViewModel = notificationViewModel;
        this.uid = uid;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        CustomNotificationItemBinding binding;

        Notification notification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomNotificationItemBinding.bind(itemView);
        }


        public void bind(Notification notification) {
            this.notification = notification;
            notificationViewModel.getCompanyNameAndImageByUid(uid, new MyListener<Company>() {
                @Override
                public void onValuePosted(Company value) {
                    if (value.getImg() == null) {
                        Glide.with(context).load(COMPANY_DEFAULT_IMAGE).placeholder(R.drawable.anim_progress).into(binding.imageCompany);
                    } else {
                        Glide.with(context).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.imageCompany);
                    }
                    binding.companyName.setText(value.getName());
                }
            });
            binding.notificationDescription.setText(notification.getBody());
        }

    }
}
