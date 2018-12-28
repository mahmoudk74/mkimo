package ashraf.example.com.communication;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ashraf on 4/3/2018.
 */

public
class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public
    void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );
        String notification_title = remoteMessage.getNotification().getTitle();
        String notification_message = remoteMessage.getNotification().getBody();

        String click_action = remoteMessage.getNotification().getClickAction();

        String from_user_id = remoteMessage.getData().get("from_user_id");


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Friend Request")
                .setContentText("You have received a new Friend Request");


        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("user_id", from_user_id);




        // set id  for notification
        int mNotificationId = (int) System.currentTimeMillis();
        // get instance notification manger

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // build notificaion and issue it

        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
