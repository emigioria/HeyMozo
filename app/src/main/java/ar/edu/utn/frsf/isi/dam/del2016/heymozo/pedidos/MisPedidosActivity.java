package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

public class MisPedidosActivity extends AppCompatActivity implements ListarMisPedidosListener {

    private RecyclerView listaPedidos;
    private ListarMisPedidosTask listarMisPedidosTask;
    private RelativeLayout loadingPanel;
    private List<Pedido> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_mis_pedidos));
        linkearVista();

        if (savedInstanceState == null) {
            listarMisPedidosTask = new ListarMisPedidosTask(this, this);
            listarMisPedidosTask.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pedidos", new Gson().toJson(pedidos));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Type datasetListType = new TypeToken<List<Pedido>>() {
        }.getType();
        pedidos = new Gson().fromJson(savedInstanceState.getString("pedidos"), datasetListType);
        listarPedidos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listarMisPedidosTask != null && listarMisPedidosTask.getStatus() != AsyncTask.Status.FINISHED) {
            listarMisPedidosTask.cancel(true);
        }
    }

    private void linkearVista() {
        listaPedidos = (RecyclerView) findViewById(R.id.listaPedidos);
        loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
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
                this.pedidos = pedidos;
                listarPedidos();
                break;
            case ListarMisPedidosTask.CANCELADO:
                finish();
                break;
            case ListarMisPedidosTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor), Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        loadingPanel.setVisibility(View.GONE);
    }

    private void listarPedidos() {
        listaPedidos.setLayoutManager(new LinearLayoutManager(this));
        listaPedidos.setAdapter(new PedidoAdapter(MisPedidosActivity.this, pedidos));
    }

    @Override
    public void busquedaIniciada() {
        Toast.makeText(this, getString(R.string.cargando_pedidos), Toast.LENGTH_SHORT).show();
        loadingPanel.setVisibility(View.VISIBLE);
    }
}
