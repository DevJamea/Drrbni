package com.example.drrbni.Service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.drrbni.Fragments.BottomNavigationScreens.NotificationsFragment;
import com.example.drrbni.Models.Notification;
import com.example.drrbni.R;
import com.example.drrbni.ViewModels.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String senderId = message.getData().get("senderUid");
        String title = message.getData().get("title");
        String body = message.getData().get("body");
        String adsId = message.getData().get("adsId");

        Repository repository = new Repository(getApplication());
        repository.storeNotification(senderId, title, body, adsId);

        showNotification(title , body );
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Repository repository = new Repository(getApplication());
        repository.updateToken(token);

    }

    private void showNotification(String title , String body ){

        //اي دي التشينل اللى بتظهر في الاعدادات
        String channelId = "App Channel";
        //بيلدر ديزاين باترن يرجع الاوبجكت بعد التعديل عليه
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , channelId);

        //نستعمل pendingIntent بدلا من الانتنت لانها تعمل في تطبيق اخر
        Intent intent = new Intent(this , NotificationsFragment.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this , 0 , intent ,0);

        builder.setOngoing(false)
                .setVibrate(new long[]{100 , 200 , 100 , 200})
                .setOnlyAlertOnce(true)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent);
        // .setContent(التصميم الخاص بنا)

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){

            NotificationChannel channel =
                    new NotificationChannel(channelId ,"الأشعارات" , NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0 , builder.build());
    }

}