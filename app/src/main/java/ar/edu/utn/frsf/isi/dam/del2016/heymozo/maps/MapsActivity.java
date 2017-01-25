package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BusquedaRestaurantesListener<Restaurante>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private GoogleMap mMap;
	private GoogleApiClient mGoogleApiClient;
	private boolean flagPermisoPedido;
	private static final int PERMISSION_REQUEST_ACCESS = 899;
	private static String TAG = "MAP";
	private RelativeLayout loadingPanel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);

		if (mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.addApi(LocationServices.API)
					.build();
		}
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

		mGoogleApiClient.connect();
	}

	@Override
	public void busquedaFinalizada(List<Restaurante> restaurantes) {
		for (Restaurante x : restaurantes) {
			LatLng rest1 = new LatLng(x.getLatitud(), x.getLongitud());
			mMap.addMarker(new MarkerOptions().position(rest1).title(x.getNombre()));
		}
		if(restaurantes.size() != 0){
			loadingPanel.setVisibility(View.GONE);
			Toast.makeText(this, getString(R.string.ubicaciones_cargadas), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void busquedaIniciada() {
		loadingPanel.setVisibility(View.VISIBLE);
		Toast.makeText(this, getString(R.string.cargando_ubicaciones), Toast.LENGTH_LONG).show();
	}

	@Override
	public void busquedaCancelada() {
		loadingPanel.setVisibility(View.GONE);
		Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
				mGoogleApiClient);
		if (mLastLocation != null) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
					.zoom(15)
					.build();
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}
}
