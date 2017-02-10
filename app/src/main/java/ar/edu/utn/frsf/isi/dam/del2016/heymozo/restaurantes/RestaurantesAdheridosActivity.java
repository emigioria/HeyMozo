package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private List<Restaurante> restaurantes;
    private View cardRestaruranteCarta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes_adheridos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_restaurantes_adheridos));
        linkearVista();

        if (savedInstanceState == null) {
            setearAnimaciones();
            listarRestaurantesTask = new ListarRestaurantesTask(this, this);
            listarRestaurantesTask.execute();
        }
    }

    private void setearAnimaciones() {
        Slide entrada = new Slide();
        getWindow().setEnterTransition(entrada);
        getWindow().setExitTransition(new Fade());

        listaRestaurantesAdheridos.setVisibility(View.GONE);
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                listaRestaurantesAdheridos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("restaurantes", new Gson().toJson(restaurantes));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Type datasetListType = new TypeToken<List<Restaurante>>() {
        }.getType();
        restaurantes = new Gson().fromJson(savedInstanceState.getString("restaurantes"), datasetListType);
        listarRestaurantes();
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

    private void linkearVista() {
        listaRestaurantesAdheridos = (RecyclerView) findViewById(R.id.listaRestaurantesAdheridos);
        loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
    }

    @Override
    public void busquedaRestauranteFinalizada(List<Restaurante> restaurantes, int resultCode) {
        switch (resultCode) {
            case ListarRestaurantesTask.OK:
                this.restaurantes = restaurantes;
                listarRestaurantes();
                break;
            case ListarRestaurantesTask.CANCELADO:
                break;
            case ListarRestaurantesTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
                break;
        }
        loadingPanel.setVisibility(View.GONE);
    }

    private void listarRestaurantes() {
        listaRestaurantesAdheridos.setLayoutManager(new LinearLayoutManager(this));
        listaRestaurantesAdheridos.setAdapter(new RestauranteAdapter(this, restaurantes));
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
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(RestaurantesAdheridosActivity.this, new Pair<>(cardRestaruranteCarta, getString(R.string.transition_card_restaurante)));
                    startActivity(i, options.toBundle());
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
    public void mostrarCarta(Restaurante restaurante, View card) {
        this.cardRestaruranteCarta = card;
        solicitarCartaTask = new SolicitarCartaTask(this, this);
        solicitarCartaTask.execute(restaurante.getId(), "");
    }
}
