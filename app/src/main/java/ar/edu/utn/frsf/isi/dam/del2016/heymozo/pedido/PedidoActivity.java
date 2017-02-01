package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

public class PedidoActivity extends AppCompatActivity {

    private Pedido pedido;
    private Gson gson = new Gson();
    private TextView textViewNombreComedor;
    private TextView textViewTotal;
    private TextView textViewEstado; //TODO estados
    private TextView textViewTiempoEspera; //TODO tiempo de espera
    private ListView listaProductos;
    private View layoutEstado;
    private View layoutTiempo;
    private ImageView imagenToolbar;
    private Button buttonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_pedido));
        linkearVista();

        pedido = gson.fromJson(getIntent().getStringExtra("pedido"), Pedido.class);

        if(pedido == null){
            this.finish();
            return;
        }

        textViewNombreComedor.setText(pedido.getNombreRestaurant());

        if (pedido.getEstado() == null) {
            layoutEstado.setVisibility(View.GONE);
        } else {
            textViewEstado.setText(pedido.getEstado());
            buttonConfirmar.setVisibility(View.GONE);
        }

        if(pedido.getImagenRestaurante()==null){
            imagenToolbar.setVisibility(View.GONE);
        }
        else{
            //TODO poner esto y borrar bar1980
             Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(pedido.getImagenRestaurante(), 0, pedido.getImagenRestaurante().length));
             imagenToolbar.setBackground(image);
            //imagenToolbar.setBackground(getDrawable(R.drawable.bar1980));
        }

        if (pedido.getFinaliza() == null) {
            layoutTiempo.setVisibility(View.GONE);
        } else {
            Long tiempoEspera = pedido.getFinaliza() - new Date().getTime();
            Long minutos = tiempoEspera / 60000 + 1;
            Long horas = minutos / 60;
            minutos = minutos % 60;
            String espera = "";
            if (horas > 0) {
                espera = horas + ((horas == 1) ? (" hora") : (" horas")) + " y " + minutos + ((minutos == 1) ? (" minuto") : (" minutos"));
            } else if (minutos > 0) {
                espera = minutos + ((minutos == 1) ? (" minuto") : (" minutos"));
            }
            textViewTiempoEspera.setText(espera);
        }

        textViewTotal.setText(String.format(Locale.getDefault(), pedido.getMoneda() + "%.2f", pedido.getTotal()));

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedido.getCodigoMesa();
                //TODO confirmar pedido
            }
        });

        listaProductos.setAdapter(new ProductoAdapter(this, pedido.getProductos()));
    }

    private void linkearVista() {
        textViewNombreComedor = (TextView) findViewById(R.id.textViewNombreComedor);
        textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        textViewEstado = (TextView) findViewById(R.id.textViewEstado);
        textViewTiempoEspera = (TextView) findViewById(R.id.textViewTiempoEspera);
        buttonConfirmar = (Button) findViewById(R.id.buttonConfirmar);
        listaProductos = (ListView) findViewById(R.id.listaProductos);
        layoutEstado = findViewById(R.id.layoutEstado);
        layoutTiempo = findViewById(R.id.layoutTiempo);
        imagenToolbar = (ImageView) findViewById(R.id.imagenToolbar);
    }
}
