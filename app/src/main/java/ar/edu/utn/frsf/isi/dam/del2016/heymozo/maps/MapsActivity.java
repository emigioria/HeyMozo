package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.CartaActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.SolicitarCartaListener;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.SolicitarCartaTask;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Carta;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BusquedaRestaurantesListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SolicitarCartaListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean flagPermisoPedido;
    private static final int PERMISSION_REQUEST_ACCESS = 899;
    private RelativeLayout loadingPanel;
    private ListarRestaurantesTask listarRestaurantesTask;
    private Map<String, Restaurante> tablaRestaurantes;
    private SolicitarCartaTask solicitarCartaTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listarRestaurantesTask != null && listarRestaurantesTask.getStatus() != AsyncTask.Status.FINISHED) {
            listarRestaurantesTask.cancel(true);
        }
        if (solicitarCartaTask != null && solicitarCartaTask.getStatus() != AsyncTask.Status.FINISHED) {
            solicitarCartaTask.cancel(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("MAP", "Mapa cargado");

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        askForPermission();

        listarRestaurantesTask = new ListarRestaurantesTask(this, this);
        listarRestaurantesTask.execute();
    }

    private void askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.pedido_permiso_titulo);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage(R.string.pedido_permiso_mensaje);
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
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MapsActivity.PERMISSION_REQUEST_ACCESS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setMyLocation();
                } else {
                    finish();
                    Toast.makeText(this, R.string.no_permission_fine_location, Toast.LENGTH_SHORT).show();
                }
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
    public void busquedaRestauranteFinalizada(List<Restaurante> restaurantes, int resultCode) {
        switch (resultCode) {
            //Correcto
            case ListarRestaurantesTask.OK:
                tablaRestaurantes = new Hashtable<>();
                for (Restaurante restaurante : restaurantes) {
                    final View marker = View.inflate(getBaseContext(), R.layout.custom_marker_layout, null);
                    final ImageView imageView = (ImageView) marker.findViewById(R.id.mapsFotoRestaurante);
                    LatLng rest1 = new LatLng(restaurante.getLatitud(), restaurante.getLongitud());
                    final Marker mapMarker = mMap.addMarker(new MarkerOptions().position(rest1).title(restaurante.getNombre()));
                    mapMarker.setTag(restaurante.getId());
                    if (restaurante.getImagen() != null && restaurante.getImagen().getUrlImagen(getBaseContext()) != null) {
                        Glide.with(getBaseContext())
                                .load(restaurante.getImagen().getUrlImagen(getBaseContext()))
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                        imageView.setImageBitmap(bitmap);
                                        mapMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(MapsActivity.this, marker)));
                                    }
                                });
                    }
                    tablaRestaurantes.put(restaurante.getId(), restaurante);
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        mostrarDialogoRestaurante(tablaRestaurantes.get(marker.getTag()));
                        return false;
                    }
                });
                Toast.makeText(this, getString(R.string.ubicaciones_cargadas), Toast.LENGTH_LONG).show();
                break;
            //Cancelado
            case ListarRestaurantesTask.CANCELADO:
                break;

            //Error de conexión
            case ListarRestaurantesTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
                break;
        }

        loadingPanel.setVisibility(View.GONE);
    }

    private void mostrarDialogoRestaurante(final Restaurante restaurante) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        View v = View.inflate(getBaseContext(), R.layout.custom_map_dialog, null);
        ImageView imageViewFotoRestaurante = (ImageView) v.findViewById(R.id.imageViewFotoRestaurante);
        TextView textViewNombreRestaurante = (TextView) v.findViewById(R.id.textViewNombreRestaurante);
        TextView textViewTelefono = (TextView) v.findViewById(R.id.textViewTelefono);
        TextView textViewDireccion = (TextView) v.findViewById(R.id.textViewDireccion);
        TextView textViewPaginaWeb = (TextView) v.findViewById(R.id.textViewPaginaWeb);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        Button buttonVerCarta = (Button) v.findViewById(R.id.buttonVerCarta);

        if (restaurante.getImagen() != null && restaurante.getImagen().getUrlImagen(getBaseContext()) != null) {
            Glide.with(getBaseContext()).load(restaurante.getImagen().getUrlImagen(getBaseContext()))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewFotoRestaurante);
        }

        if (restaurante.getNombre() != null) {
            textViewNombreRestaurante.setText(restaurante.getNombre());
        } else {
            textViewNombreRestaurante.setVisibility(View.GONE);
        }
        if (restaurante.getDireccion() != null) {
            textViewDireccion.setText(restaurante.getDireccion());
        } else {
            textViewDireccion.setVisibility(View.GONE);
        }
        if (restaurante.getTelefono() != null) {
            textViewTelefono.setText(restaurante.getTelefono());
        } else {
            textViewTelefono.setVisibility(View.GONE);
        }
        if (restaurante.getPagina() != null) {
            textViewPaginaWeb.setText(restaurante.getPagina());
        } else {
            textViewPaginaWeb.setVisibility(View.GONE);
        }
        if (restaurante.getRating() != null) {
            ratingBar.setRating(restaurante.getRating());
        } else {
            ratingBar.setVisibility(View.GONE);
        }
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Cambiar por actividad para ver calificaciones en el futurooooo
                Toast.makeText(getBaseContext(), "Caificaciones: Muy buenas!", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        buttonVerCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarCartaTask = new SolicitarCartaTask(MapsActivity.this, MapsActivity.this);
                solicitarCartaTask.execute(restaurante.getId(), "");
            }
        });

        builder.setView(v);
        builder.show();
    }

    @Override
    public void busquedaRestauranteIniciada() {
        loadingPanel.setVisibility(View.VISIBLE);
        Toast.makeText(this, getString(R.string.cargando_ubicaciones), Toast.LENGTH_LONG).show();
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

    // Convert a view to bitmap
    private Bitmap createDrawableFromView(Activity actividad, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        actividad.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void busquedaIniciada() {
        Toast.makeText(getApplicationContext(), R.string.mensaje_esperando_carta, Toast.LENGTH_SHORT).show();
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void busquedaFinalizada(String cartaJSON, String mesaJSON, int status) {
        switch (status) {
            case SolicitarCartaTask.OK:
                Gson gson = new Gson();
                Carta carta = gson.fromJson(cartaJSON, Carta.class);
                if (carta != null) {
                    Intent i = new Intent(this, CartaActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("carta", cartaJSON);
                    extras.putString("mesa", mesaJSON);
                    extras.putBoolean("noHacerPedidos", true);
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Toast.makeText(this, R.string.restaurante_sin_carta, Toast.LENGTH_LONG).show();
                }
                break;
            case SolicitarCartaTask.CANCELADO:
                Toast.makeText(this, R.string.mensaje_solicitud_cancelada, Toast.LENGTH_LONG).show();
                break;
            case SolicitarCartaTask.ERROR:
                Toast.makeText(this, R.string.mensaje_error_sin_conexion, Toast.LENGTH_LONG).show();
                break;
        }
        loadingPanel.setVisibility(View.GONE);
    }

}
