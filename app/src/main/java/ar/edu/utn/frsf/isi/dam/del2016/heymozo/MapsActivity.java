package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;

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

		//TODO pedir permisos
		mMap.setMyLocationEnabled(true);

		//TODO pedir al servidor la lista de ubicaciones
		LatLng rest1 = new LatLng(-31.6387288,-60.6936089);
		LatLng rest2 = new LatLng(-31.6366443,-60.699605);
		mMap.addMarker(new MarkerOptions().position(rest1).title("Paladar Negro"));
		mMap.addMarker(new MarkerOptions().position(rest2).title("1980"));
		Log.d("MAP", "Ubicaciones de restaurantes cargadas");
	}
}
