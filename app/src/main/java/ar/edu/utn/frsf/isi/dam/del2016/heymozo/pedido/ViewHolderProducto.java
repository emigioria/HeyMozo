package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.view.View;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class ViewHolderProducto {
    TextView cantidad;
    TextView nombreProducto;
    TextView moneda;
    TextView precio;

    ViewHolderProducto(View viewBase) {
        this.cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
    }
}
