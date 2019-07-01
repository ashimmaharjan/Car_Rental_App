package Broadcast;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import Channels.NotificationChannels;
import com.example.carrental.R;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    private NotificationManagerCompat notificationManagerCompat;
    Context context;

    public BroadcastReceiver(Context context)
    {
        this.context=context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
         boolean noConnectivity;
        notificationManagerCompat=NotificationManagerCompat.from(context);
        NotificationChannels notificationChannels=new NotificationChannels(context);
        notificationChannels.createChannel();

         if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
         {
             noConnectivity=intent.getBooleanExtra(
                     ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                     false);

             if (noConnectivity)
             {
                 Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show();
                 DisplayNotification();
             }
             else {
                 Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
                 DisplayNotification2();
             }
         }
    }

    private void DisplayNotification()
    {
        Notification notification=new NotificationCompat.Builder(context, NotificationChannels.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_rss_feed_black_24dp)
                .setContentTitle("No connection")
                .setContentText("No network connectivity detected. Please connect to a network")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(1,notification);
    }

    private void DisplayNotification2()
    {
        Notification notification=new NotificationCompat.Builder(context,NotificationChannels.CHANNEL_2)
                .setSmallIcon(R.drawable.ic_rss_feed_black_24dp)
                .setContentTitle("Connected")
                .setContentText("Network connection detected.")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(2,notification);
    }
}
