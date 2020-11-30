package com.headshot.discountforme.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.R;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


/**
 * Created by cz on 22/02/2018.
 */

public class MyFcmPushReceiver extends FirebaseMessagingService implements LifecycleOwner {
    String TAG = "firebase";
    String NOTIFICATION_CHANNEL_ID = "10001";
    String message, body;
    public static final String MESSAGE_STATUS = "message_status";
    SharedPrefManager sharedPrefManager;


    public MyFcmPushReceiver() {

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,"Refreshed token: " + refreshedToken);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("m_token",refreshedToken);
        edit.apply();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("mnmaa","smsmsms,ms");
        // TODO(developer): Handle FCM messages here
        showNotification(remoteMessage.getData());
    }


    private void showNotification(Map<String, String> notificationr) {
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        Log.e("pushpush",notificationr.toString());


        try {
            JSONObject jo = new JSONObject(notificationr);

            Intent notificationIntent;
            notificationIntent = new Intent(getApplicationContext(),HomeActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pushOrderNotification(notificationIntent,jo.getString("title"),jo.getString("body"));

            Log.e("jo",jo.toString());

        } catch (Exception e) {
            Log.e("pushpushexception",e.toString());
            e.printStackTrace();
        }


    }

    private void pushOrderNotification(Intent notificationIntent,String title,String body) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = null;

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(bitmap1)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(contentIntent);
        } else {
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setLargeIcon(bitmap1)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(contentIntent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel;
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION_CHANNEL_NAME",importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            assert notificationManager != null;
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(body);
            notificationBuilder.setSmallIcon(R.drawable.logo);
            notificationBuilder.setLargeIcon(bitmap1);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Random random = new Random();
        int notificationID = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(notificationID,notificationBuilder.build());

    }


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();

    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
