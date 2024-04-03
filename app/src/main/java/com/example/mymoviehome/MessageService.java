package com.example.mymoviehome;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.example.mymoviehome.fragmentHelper.eaStackHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {

    private NotificationCompat.Builder builder;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String content = remoteMessage.getNotification().getBody();
        Uri image= remoteMessage.getNotification().getImageUrl();
        Log.e("Title",title);
        Log.e("Content",content);



        durumaBagli(title,content);
    }


    public void durumaBagli(String title,String content)
    {

        NotificationManager bildirimYoneticisi =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent gidilecekIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
     //   returnHomePageActivity().finish();




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if(kanal == null){

                kanal = new NotificationChannel(kanalId,kanalAd,kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(this,kanalId);

            builder.setContentTitle(title);
            builder.setContentText(content);
            //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(content))
                    .build();

            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setAutoCancel(true);
          //  builder.setContentIntent(gidilecekIntent);

        }else{

            builder = new NotificationCompat.Builder(this,"");

            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.ic_notification);
            //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(content))
                    .build();
            builder.setAutoCancel(true);
         //   builder.setContentIntent(gidilecekIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);

        }

        bildirimYoneticisi.notify(1,builder.build());

    }
}
