package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Carta;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Mesa;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Seccion;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;

public class CartaActivity extends AppCompatActivity implements CartaListener {

    private static final int CODIGO_PEDIDO = 107;
    private Carta carta;
    private Mesa mesa;
    private Boolean noHacerPedidos;
    private FloatingActionButton fab;
    private SharedPreferences preferenciasAyuda;
    private LinearLayout mensajeAyudaRealizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mensajeAyudaRealizarPedido = (LinearLayout) findViewById(R.id.ayuda_realizar_pedido_mensaje);
        Button btnAyudaRealizarPedido = (Button) findViewById(R.id.entendido_ayuda_realizar_pedido_button);
        preferenciasAyuda = getSharedPreferences(getString(R.string.preferencia_ayuda), Context.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getWindow().setExitTransition(new Fade());

        Gson gson = new Gson();
        if (savedInstanceState == null) {
            carta = gson.fromJson(getIntent().getExtras().getString("carta"), Carta.class);
        } else {
            carta = new Gson().fromJson(savedInstanceState.getString("carta"), Carta.class);
        }
        mesa = gson.fromJson(getIntent().getExtras().getString("mesa"), Mesa.class);
        noHacerPedidos = getIntent().getExtras().getBoolean("noHacerPedidos");
        if (carta == null || mesa == null) {
            finishAfterTransition();
            return;
        }
        if (noHacerPedidos == null) {
            noHacerPedidos = false;
        }

        setTitle(carta.getRestaurante().getNombre());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (!preferenciasAyuda.getBoolean(getString(R.string.key_ayuda_realizar_pedido), true)) {
            mensajeAyudaRealizarPedido.setVisibility(View.GONE);
        }
        btnAyudaRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editorPreferencias = preferenciasAyuda.edit();
                editorPreferencias.putBoolean(getString(R.string.key_ayuda_realizar_pedido),false);
                editorPreferencias.apply();
                mensajeAyudaRealizarPedido.setVisibility(View.GONE);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedido pedido = new Pedido()
                        .setMesa(mesa)
                        .setRestaurante(carta.getRestaurante());
                ArrayList<Producto> productosSeleccionados = new ArrayList<>();
                for (Seccion seccion : carta.getSecciones()) {
                    for (Producto p : seccion.getProductos()) {
                        if (p.getCantidad() != 0) {
                            productosSeleccionados.add(p);
                        }
                    }
                }
                pedido.setProductos(productosSeleccionados);

                if (productosSeleccionados.size() > 0) {
                    Intent intent = new Intent(CartaActivity.this, PedidoActivity.class);
                    intent.putExtra("pedido", pedido.toJSONObject());
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CartaActivity.this);
                    startActivityForResult(intent, CODIGO_PEDIDO, options.toBundle());
                }
            }
        });
        if (noHacerPedidos) {
            ((ViewGroup) fab.getParent()).removeView(fab);
            mensajeAyudaRealizarPedido.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("carta", new Gson().toJson(carta));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Carta getCarta() {
        return carta;
    }

    @Override
    public Boolean getNoHacerPedidos() {
        return noHacerPedidos;
    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }

    @Override
    public LinearLayout getMensajeAyudaRealizarPedido() {
        return mensajeAyudaRealizarPedido;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODIGO_PEDIDO:
                if (resultCode == RESULT_OK) {
                    finishAfterTransition();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private LinearLayout mensajeAyudaRealizarPedido;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                    R.layout.fragment_carta, container, false);
            setupRecyclerView(recyclerView);
            return recyclerView;
        }

        private void setupRecyclerView(RecyclerView recyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            final CartaListener cartaListener = (CartaListener) getActivity();
            final Carta carta = cartaListener.getCarta();
            final Boolean noHacerPedidos = cartaListener.getNoHacerPedidos();
            final FloatingActionButton fab = cartaListener.getFab();
            final SharedPreferences preferenciasAyuda = recyclerView.getContext().getSharedPreferences("ayuda", Context.MODE_PRIVATE);
            mensajeAyudaRealizarPedido = cartaListener.getMensajeAyudaRealizarPedido();

            final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), carta.getRestaurante().getMoneda(), noHacerPedidos, carta.getSecciones().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getProductos());
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 || dy < 0 && fab.isShown()) {
                        mensajeAyudaRealizarPedido.setVisibility(View.GONE);
                        fab.hide();
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if(!noHacerPedidos && preferenciasAyuda.getBoolean(getString(R.string.key_ayuda_realizar_pedido),true)){
                            mensajeAyudaRealizarPedido.setVisibility(View.VISIBLE);
                        }

                        fab.show();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            //Cantidad de secciones que contenga la carta
            return carta.getSecciones().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // retorna los titulos de cada seccion según el orden en el que se escribió la carta
            return carta.getSecciones().get(position).getNombre();
        }
    }
}
