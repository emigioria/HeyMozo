package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.Pedido;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

public class CartaActivity extends AppCompatActivity {

    private static Carta carta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        try {
            carta = new Carta(getIntent().getExtras().getString("carta"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setTitle(carta.getNombreRestaurant());

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedido pedido = new Pedido()
                        .setCodigoMesa(getIntent().getExtras().getString("mesa"))
                        .setMoneda(getIntent().getExtras().getString("moneda"))
                        .setNombreRestaurant(carta.getNombreRestaurant());
                ArrayList<Producto> productosSeleccionados = new ArrayList<>();
                int i = 0;
                for (String seccion : carta.getSecciones()) {
                    for (Producto p : carta.getProductosSeccion(i)) {
                        if (p.getCantidad() != 0) {
                            productosSeleccionados.add(p);
                        }
                    }
                    i++;
                }
                pedido.setProductos(productosSeleccionados);

                if (productosSeleccionados.size() > 0) {
                    Intent intent = new Intent(CartaActivity.this, PedidoActivity.class);
                    intent.putExtra("pedido", pedido.toJSONObject());
                    startActivity(intent);
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

        switch (id){
            case R.id.action_more_information: return true; //TODO
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carta, container, false);
            ListView seccionListView = (ListView) rootView.findViewById(R.id.seccion_listview);

            seccionListView.setAdapter(new SeccionAdapter(getContext(), carta.getProductosSeccion(getArguments().getInt(ARG_SECTION_NUMBER) - 1)));

            return rootView;
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
            return carta.getSecciones().get(position);
        }
    }
}
