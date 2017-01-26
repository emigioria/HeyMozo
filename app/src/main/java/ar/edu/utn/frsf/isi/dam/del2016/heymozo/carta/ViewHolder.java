package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

/**
 * Created by lucas on 25/01/17.
 */

class ViewHolder {
    TextView cantidad;
    TextView nombreProducto;
    TextView moneda;
    TextView precio;
    LinearLayout secondLayout;
    ImageView agregar;
    ImageView quitar;

    ViewHolder(View viewBase){
        this.cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        this.secondLayout = (LinearLayout) viewBase.findViewById(R.id.second_layout);
        this.agregar = (ImageView) secondLayout.findViewById(R.id.agregar_imageview);
        this.quitar = (ImageView) secondLayout.findViewById(R.id.quitar_imageview);
    }
}
