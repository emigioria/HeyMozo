package ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseService extends FirebaseInstanceIdService {
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("APP", "iniciado servicio Firebase");
	}

	@Override
	public void onTokenRefresh() {
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		Log.d("APP", "onTokenRefresh: " + refreshedToken);
		saveTokenToPrefs(refreshedToken);
	}

	private void saveTokenToPrefs(String _token) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("registration_id", _token);
		editor.apply();
	}
}