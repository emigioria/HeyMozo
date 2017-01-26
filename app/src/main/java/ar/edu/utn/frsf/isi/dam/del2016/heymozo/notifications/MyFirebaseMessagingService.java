package ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.MainActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

/**
 * Created by daniel on 23/01/17.
 */


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
	}

	private void sendNotification(String messageTitle,String messageBody) {
	android.support.v4.app.NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.ic_stat_name)
						.setContentTitle(messageTitle)
						.setLights(Color.argb(1, 255, 128, 0), 100, 0)
						.setVibrate(new long[] { 1000, 1000})
						.setContentText(messageBody);

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