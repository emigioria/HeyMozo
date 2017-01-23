package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.Restaurante;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BusquedaRestaurantesListener<Restaurante> {

	private GoogleMap mMap;
	private boolean flagPermisoPedido;
	private static final int PERMISSION_REQUEST_ACCESS = 899;
	private static String TAG = "MAP";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		Log.d(TAG, "Mapa cargado");

		askForPermission();

		new ListarRestaurantesTask(this).execute();
	}

	public void askForPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(this,
						android.Manifest.permission.ACCESS_FINE_LOCATION)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Permisos Peligrosos!!!");
					builder.setPositiveButton(android.R.string.ok, null);
					builder.setMessage("Puedo acceder a un permiso peligroso???");
					builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@TargetApi(Build.VERSION_CODES.M)
						@Override
						public void onDismiss(DialogInterface dialog) {
							flagPermisoPedido = true;
							requestPermissions(
									new String[]
											{android.Manifest.permission.ACCESS_FINE_LOCATION}
									, PERMISSION_REQUEST_ACCESS);
						}
					});
					builder.show();
				} else {
					flagPermisoPedido = true;
					ActivityCompat.requestPermissions(this,
							new String[]
									{android.Manifest.permission.ACCESS_FINE_LOCATION}
							, PERMISSION_REQUEST_ACCESS);
				}

			}
		}
		if (!flagPermisoPedido) {
			setMyLocation();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       String permissions[], int[] grantResults) {
		switch (requestCode) {
			case MapsActivity.PERMISSION_REQUEST_ACCESS: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					setMyLocation();
				} else {
					finish();
					Toast.makeText(this, "No permission for access fine location", Toast.LENGTH_SHORT).show();
				}
				return;
			}
		}
	}

	private void setMyLocation() {
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		mMap.setMyLocationEnabled(true);
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();

		Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		if (location != null) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(location.getLatitude(), location.getLongitude()))
					.zoom(14)
					.build();
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
	}

	@Override
	public void busquedaFinalizada(List<Restaurante> restaurantes) {
		for(Restaurante x : restaurantes){
			LatLng rest1 = new LatLng(x.getLatitud(), x.getLongitud());
			mMap.addMarker(new MarkerOptions().position(rest1).title(x.getNombre()));
		}
		Log.d(TAG, "Ubicaciones de restaurantes cargadas");
	}

	@Override
	public void busquedaIniciada() {
	}
}
