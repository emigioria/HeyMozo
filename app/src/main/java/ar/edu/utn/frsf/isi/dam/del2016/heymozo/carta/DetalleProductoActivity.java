package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.ProductoDetallado;

public class DetalleProductoActivity extends AppCompatActivity {

    private ProductoDetallado producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_detalle_producto);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ImageView imagen = (ImageView) findViewById(R.id.detalle_imageview);
        final TextView detallesTextView = (TextView) findViewById(R.id.detalles_textview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            producto = new Gson().fromJson(getIntent().getStringExtra("producto"), ProductoDetallado.class);
        } else {
            producto = new Gson().fromJson(savedInstanceState.getString("producto"), ProductoDetallado.class);
        }
        if (producto == null) {
            finish();
            return;
        }

        Glide.with(getApplicationContext()).load(producto.getImagen().getUrlImagen(getApplicationContext()))
                .error(getApplicationContext().getDrawable(R.drawable.ic_broken_image_black_24dp))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                              @Override
                              public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                  progressBar.setVisibility(View.GONE);
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                  progressBar.setVisibility(View.GONE);
                                  return false;
                              }
                          }
                )
                .into(new ImageViewTarget<GlideDrawable>(imagen) { //Necesario para dibujar bien el CenterCrop
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imagen.setImageDrawable(resource);
                    }
                });

        setTitle(producto.getNombre());
        detallesTextView.setText(producto.getDescripcionLarga());

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("producto", new Gson().toJson(producto));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}