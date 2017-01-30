package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.Pedido;

public class MisPedidos extends AppCompatActivity {

    private ListView listaPedidos;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create the type for the collection. In this case define that the collection is of type Dataset
        Type datasetListType = new TypeToken<ArrayList<Pedido>>() {
        }.getType();
        ArrayList<Pedido> pedidos = gson.fromJson(getIntent().getStringExtra("pedidos"), datasetListType);

        listaPedidos.setAdapter(new PedidoAdapter(this, pedidos));
    }

    private void linkearVista() {
        listaPedidos = (ListView) findViewById(R.id.listaPedidos);
    }
}
