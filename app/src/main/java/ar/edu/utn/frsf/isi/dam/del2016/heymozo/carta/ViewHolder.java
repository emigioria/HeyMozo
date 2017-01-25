package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.view.View;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

/**
 * Created by lucas on 25/01/17.
 */

class ViewHolder {
    TextView nombreProducto;
    TextView precio;
    TextView moneda;

    ViewHolder(View viewBase){
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
    }
}
