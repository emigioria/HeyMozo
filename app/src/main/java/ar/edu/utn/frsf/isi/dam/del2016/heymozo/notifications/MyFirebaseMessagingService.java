package ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.inicio.MainActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private SharedPreferences preferences;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(getString(R.string.notifications_new_message), true)) {
            String pedidoId = remoteMessage.getData().get("pedidoId");
            if (pedidoId != null && !pedidoId.isEmpty()) {
                sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), pedidoId);
            } else {
                sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
            }
        }
    }

    private void sendNotification(String messageTitle, String messageBody, String pedidoId) {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
        String color = preferences.getString(getString(R.string.notifications_new_message_light), "-1");
        if (!color.equals("-1")) {
            mBuilder.setLights(Color.parseColor(color), 100, 0);
        }
        Boolean vibrar = preferences.getBoolean(getString(R.string.notifications_new_message_vibrate), false);
        if (vibrar) {
            mBuilder.setVibrate(new long[]{0, 1000});
        }
        String strRingtonePreference = preferences.getString(getString(R.string.notifications_new_message_ringtone), "DEFAULT_SOUND");
        mBuilder.setSound(Uri.parse(strRingtonePreference));

        Intent resultIntent = new Intent(this, PedidoActivity.class);

        resultIntent.putExtra("pedidoId", pedidoId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(PedidoActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

    private void sendNotification(String messageTitle, String messageBody) {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
        String color = preferences.getString(getString(R.string.notifications_new_message_light), "-1");
        if (!color.equals("-1")) {
            mBuilder.setLights(Color.parseColor(color), 100, 0);
        }
        Boolean vibrar = preferences.getBoolean(getString(R.string.notifications_new_message_vibrate), false);
        if (vibrar) {
            mBuilder.setVibrate(new long[]{0, 1000});
        }
        String strRingtonePreference = preferences.getString(getString(R.string.notifications_new_message_ringtone), "DEFAULT_SOUND");
        mBuilder.setSound(Uri.parse(strRingtonePreference));

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }


}
