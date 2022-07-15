package com.example.drrbni.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.drrbni.Constant.COMPANY_DEFAULT_IMAGE;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbni.Fragments.BottomNavigationScreens.NotificationsFragmentDirections;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Company;
import com.example.drrbni.Models.Notification;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.NotificationViewModel;
import com.example.drrbni.databinding.CustomNotificationItemBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private NotificationViewModel notificationViewModel;

    public NotificationAdapter(List<Notification> notifications, NotificationViewModel notificationViewModel) {
        this.notifications = notifications;
        this.notificationViewModel = notificationViewModel;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        CustomNotificationItemBinding binding;
        Notification notification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomNotificationItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationViewModel.getAdsById(notification.getAdsId(), new MyListener<Ads>() {
                        @Override
                        public void onValuePosted(Ads value) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(NotificationsFragmentDirections
                                    .actionNotificationsFragmentToShowPostFragment(value));
                        }
                    });
                }
            });
        }

        public void bind(Notification notification){
            this.notification = notification;
            binding.progressBar.setVisibility(View.VISIBLE);
            notificationViewModel.getSenderImgAndName(notification.getSenderUid(), new MyListener<Company>() {
                @Override
                public void onValuePosted(Company value) {

                    if (value.getImg() != null){
                        Glide.with(binding.senderImg.getContext()).load(value.getImg()).addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(binding.senderImg);
                    }else {
                        Glide.with(binding.senderImg.getContext()).load(COMPANY_DEFAULT_IMAGE).addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                               binding.progressBar.setVisibility(View.GONE);
                               return false;
                            }
                        }).into(binding.senderImg);
                    }

                }
            });
            binding.notificationBody.setText(notification.getBody());
            binding.notificationTitle.setText(notification.getTitle());
        }
    }
}
