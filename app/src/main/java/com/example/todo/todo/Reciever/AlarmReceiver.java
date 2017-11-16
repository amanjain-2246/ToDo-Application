package com.example.todo.todo.Reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.todo.todo.Activity.SelectionActivity;
import com.example.todo.todo.R;

/**
 * Created by .hp on 13-06-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context,SelectionActivity.class);
        i.putExtra("Title",title);
        i.putExtra("Description",description);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,  i , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL).addAction(R.drawable.notification_action,"tab to choose action",pendingIntent);


        notificationManager.notify(100, builder.build());

    }
}
