package com.mad.madproject.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mad.madproject.R;
import com.mad.madproject.main.MainActivity;

/**
 * Created by limyandivicotrico on 5/27/18.
 */

/**
 * Service that handles sending notification to user when the trip time is in a week or in 3 day.
 * TODO: Handle the notifation sent by the server.
 */
public class FirebaseMessagingNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage != null && remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    /**
     * Method to handle building the notification and sent it.
     * @param messageBody the content of the messageBody.
     */
    private void sendNotification(String messageBody) {
        //create the service intent that open the main activity.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //give permissions to send the notification even though the application is not currently opened. (Open the activity in one shot for the activity.
        //Request code is 0 because we are currently not sending any request code in this version of the application.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //get the default notification ringtone for notification.
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //create the notificationbuilder, autocancel true means that once user click it first time, it will disappear from the notification bar.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Riote Travelapp")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //id is currently set to 0 just to make sure that the notification work first.
        notificationManager.notify(0, notificationBuilder.build());
    }
}
