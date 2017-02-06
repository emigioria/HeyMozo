package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Carta;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Mesa;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Seccion;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;

public class CartaActivity extends AppCompatActivity {

    private static final int CODIGO_PEDIDO = 107;
    private Carta carta;
    private Mesa mesa;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        Gson gson = new Gson();
        carta = gson.fromJson(getIntent().getExtras().getString("carta"), Carta.class);
        mesa = gson.fromJson(getIntent().getExtras().getString("mesa"), Mesa.class);
        if (carta == null || mesa == null) {
            CartaActivity.this.finish();
            return;
        }

        setTitle(carta.getRestaurante().getNombre());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                    startActivityForResult(intent, CODIGO_PEDIDO);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_more_information:
                return true; //TODO
        }

        return super.onOptionsItemSelected(item);
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
        private Carta carta;
        private FloatingActionButton fab;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Carta carta, FloatingActionButton fab) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.setCarta(carta);
            fragment.setFab(fab);
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
            Log.v("MONEDA", carta.getRestaurante().getMoneda().getSimbolo());
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), carta.getRestaurante().getMoneda(), carta.getSecciones().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getProductos());
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 || dy < 0 && fab.isShown())
                        fab.hide();
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        fab.show();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

        public void setCarta(Carta carta) {
            this.carta = carta;
        }

        public void setFab(FloatingActionButton fab) {
            this.fab = fab;
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
            return PlaceholderFragment.newInstance(position + 1, carta, fab);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODIGO_PEDIDO:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
