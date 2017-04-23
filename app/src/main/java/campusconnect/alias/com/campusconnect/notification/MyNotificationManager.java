package campusconnect.alias.com.campusconnect.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import campusconnect.alias.com.campusconnect.R;

/**
 * Created by alias on 4/21/2017.
 */

public class MyNotificationManager {

    private Context mContext;
    private static final int NOTIFICATION_ID = 2341;

    public MyNotificationManager (Context context){
        mContext = context;
    }

    public void showNotification(String from , String notification , Intent intent){
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        Notification mNotification = builder.setSmallIcon(R.mipmap.icon_complete)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification);

    }
}
