package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;
	private boolean flagPermisoPedido;
	private static final int PERMISSION_REQUEST_ACCESS = 899;

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
		Log.d("MAP", "Mapa cargado");

		askForPermission();

		//TODO pedir al servidor la lista de ubicaciones
		LatLng rest1 = new LatLng(-31.6387288,-60.6936089);
		LatLng rest2 = new LatLng(-31.6366443,-60.699605);
		mMap.addMarker(new MarkerOptions().position(rest1).title("Paladar Negro"));
		mMap.addMarker(new MarkerOptions().position(rest2).title("1980"));
		Log.d("MAP", "Ubicaciones de restaurantes cargadas");
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
					.target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
					.zoom(14)                   // Sets the zoom
					.build();                   // Creates a CameraPosition from the builder
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
	}
}
