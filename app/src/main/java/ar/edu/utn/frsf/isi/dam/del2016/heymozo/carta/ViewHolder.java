package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.view.View;
import android.widget.ImageButton;
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
    ImageButton borrarImagenBtn;

    ViewHolder(View viewBase){
        this.cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        this.borrarImagenBtn = (ImageButton) viewBase.findViewById(R.id.borrar_imagebtn);
    }
}
