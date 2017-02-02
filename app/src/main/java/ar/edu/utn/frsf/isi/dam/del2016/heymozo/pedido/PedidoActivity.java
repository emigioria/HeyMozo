package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

public class PedidoActivity extends AppCompatActivity implements GuardarPedidoListener {

    private Pedido pedido;
    private Gson gson = new Gson();
    private TextView textViewNombreComedor;
    private TextView textViewTotal;
    private TextView textViewEstado;
    private TextView textViewTiempoEspera;
    private ListView listaProductos;
    private View layoutEstado;
    private View layoutTiempo;
    private ImageView imagenToolbar;
    private Button buttonConfirmar;

    private GuardarPedidoTask guardarPedidoTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_pedido));
        linkearVista();

        pedido = gson.fromJson(getIntent().getStringExtra("pedido"), Pedido.class);

        if (pedido == null) {
            this.finish();
            return;
        }

        textViewNombreComedor.setText(pedido.getRestaurante().getNombre());

        if (pedido.getEstado() == null) {
            layoutEstado.setVisibility(View.GONE);
        } else {
            textViewEstado.setText(pedido.getEstado());
            buttonConfirmar.setVisibility(View.GONE);
        }

        if (pedido.getRestaurante().getImagen() == null) {
            imagenToolbar.setVisibility(View.GONE);
        } else {
            Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(pedido.getRestaurante().getImagen(), 0, pedido.getRestaurante().getImagen().length));
            imagenToolbar.setBackground(image);
        }

        if (pedido.getFinaliza() == null) {
            layoutTiempo.setVisibility(View.GONE);
        } else {
            Long tiempoEspera = pedido.getFinaliza() - new Date().getTime();
            if (tiempoEspera > 0) {
                setEspera(tiempoEspera);
                new CountDownTimer(tiempoEspera, 60000) {
                    public void onTick(long millisUntilFinished) {
                        setEspera(millisUntilFinished);
                    }

                    public void onFinish() {
                        textViewTiempoEspera.setText(R.string.entrega_en_instantes);
                    }
                }.start();
            } else {
                textViewTiempoEspera.setText(R.string.entrega_en_instantes);
                pedido.setFinaliza((Long) null);
            }
        }

        textViewTotal.setText(String.format(Locale.getDefault(), pedido.getMoneda().getSimbolo() + "%.2f", pedido.getTotal()));

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PedidoActivity.this);
                String userId = preferences.getString("registration_id", "no id");
                pedido.setUserID(userId);
                pedido.setEstado(getString(R.string.estado_pedido_preparando));
                pedido.setFechaPedido(new Date().getTime());

                guardarPedidoTask = new GuardarPedidoTask(PedidoActivity.this, PedidoActivity.this);
                guardarPedidoTask.execute(pedido);

                buttonConfirmar.setText(getString(R.string.guardando_pedido));
                buttonConfirmar.setOnClickListener(null);
            }
        });

        listaProductos.setAdapter(new ProductoAdapter(this, pedido.getProductos()));
    }

    private void setEspera(Long tiempoEspera) {
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

    @Override
    protected void onPause() {
        if (guardarPedidoTask != null) {
            guardarPedidoTask.cancel(true);
        }
        super.onPause();
    }

    @Override
    public void guardadoFinalizado(int resultCode) {
        switch (resultCode) {
            case GuardarPedidoTask.OK:
                Toast.makeText(this, getString(R.string.pedido_guardado_correcto), Toast.LENGTH_LONG).show();
                break;
            case GuardarPedidoTask.CANCELADO:
                Toast.makeText(this, getString(R.string.pedido_no_guardado), Toast.LENGTH_LONG).show();
                break;
            case GuardarPedidoTask.ERROR:
                Toast.makeText(this, getString(R.string.error_servidor) + "\n" + getString(R.string.pedido_no_guardado), Toast.LENGTH_LONG).show();
                break;
        }
        finish();
    }

    @Override
    public void guardadoIniciado() {
        Toast.makeText(this, R.string.guardando_pedido, Toast.LENGTH_LONG).show();
    }
}
