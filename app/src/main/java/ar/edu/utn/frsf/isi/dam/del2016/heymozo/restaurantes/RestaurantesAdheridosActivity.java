package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.CartaActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.SolicitarCartaListener;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.SolicitarCartaTask;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.BusquedaRestaurantesListener;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.ListarRestaurantesTask;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Carta;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

public class RestaurantesAdheridosActivity extends AppCompatActivity implements BusquedaRestaurantesListener, SolicitarCartaListener, MostrarCartaListener {

    private RecyclerView listaRestaurantesAdheridos;
    private ListarRestaurantesTask listarRestaurantesTask;
    private SolicitarCartaTask solicitarCartaTask;
    private RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes_adheridos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_restaurantes_adheridos));
        linkearVista();

        listarRestaurantesTask = new ListarRestaurantesTask(this, this);
        listarRestaurantesTask.execute();
    }

    @Override
    protected void onPause() {
        if (listarRestaurantesTask != null) {
            listarRestaurantesTask.cancel(true);
        }
        if (solicitarCartaTask != null) {
            solicitarCartaTask.cancel(true);
        }
        super.onPause();
    }

    private void linkearVista() {
        listaRestaurantesAdheridos = (RecyclerView) findViewById(R.id.listaRestaurantesAdheridos);
        loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
    }

    @Override
    public void busquedaRestauranteFinalizada(List<Restaurante> restaurantes, int resultCode) {
        switch (resultCode) {
            case ListarRestaurantesTask.OK:
                listaRestaurantesAdheridos.setLayoutManager(new LinearLayoutManager(this));
                listaRestaurantesAdheridos.setAdapter(new RestauranteAdapter(this, this, restaurantes));
                break;
            case ListarRestaurantesTask.CANCELADO:
                break;
            case ListarRestaurantesTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
                break;
        }
        loadingPanel.setVisibility(View.GONE);
    }

    @Override
    public void busquedaRestauranteIniciada() {
        Toast.makeText(this, R.string.cargando_restaurantes, Toast.LENGTH_SHORT).show();
        loadingPanel.setVisibility(View.VISIBLE);
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

    @Override
    public void mostrarCarta(Restaurante restaurante) {
        solicitarCartaTask = new SolicitarCartaTask(this, this);
        solicitarCartaTask.execute(restaurante.getId(), "");
    }
}
