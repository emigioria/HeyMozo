package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.ProductoDetallado;

public class DetalleProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imagen = (ImageView) findViewById(R.id.detalle_imageview);
        TextView detallesTextView = (TextView) findViewById(R.id.detalles_textview);

        setSupportActionBar(toolbar);

        ProductoDetallado producto = new Gson().fromJson(getIntent().getStringExtra("producto"), ProductoDetallado.class);

        Glide.with(getApplicationContext()).load(producto.getImagen().getUrlImagen(getApplicationContext()))
                .error(getApplicationContext().getDrawable(R.drawable.ic_broken_image_black_24dp))
                .placeholder(getApplicationContext().getDrawable(R.drawable.ic_loading))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagen);

        setTitle(producto.getNombre());
        detallesTextView.setText(producto.getDescripcionLarga());

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}