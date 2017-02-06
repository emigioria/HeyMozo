package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

public class MisPedidosActivity extends AppCompatActivity implements ListarMisPedidosListener {

    private RecyclerView listaPedidos;
    private ListarMisPedidosTask listarMisPedidosTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linkearVista();

        listarMisPedidosTask = new ListarMisPedidosTask(this, this);
        listarMisPedidosTask.execute();
    }

    @Override
    protected void onPause() {
        if (listarMisPedidosTask != null) {
            listarMisPedidosTask.cancel(true);
        }
        super.onPause();
    }

    private void linkearVista() {
        listaPedidos = (RecyclerView) findViewById(R.id.listaPedidos);
    }

    @Override
    public void busquedaFinalizada(List<Pedido> pedidos, int resultCode) {
        switch (resultCode) {
            case ListarMisPedidosTask.OK:
                if (pedidos == null) {
                    finish();
                    return;
                }

                Collections.sort(pedidos, new Comparator<Pedido>() {
                    @Override
                    public int compare(Pedido o1, Pedido o2) {
                        return -(o1.getFechaPedido().compareTo(o2.getFechaPedido()));
                    }
                });
                listaPedidos.setLayoutManager(new LinearLayoutManager(this));
                listaPedidos.setAdapter(new PedidoAdapter(MisPedidosActivity.this, new ArrayList<>(pedidos)));
                break;
            case ListarMisPedidosTask.CANCELADO:
                finish();
                break;
            case ListarMisPedidosTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    @Override
    public void busquedaIniciada() {
        Toast.makeText(this, getString(R.string.cargando_pedidos), Toast.LENGTH_SHORT).show();
    }
}
