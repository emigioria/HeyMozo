package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    final TextView cantidad;
    private final TextView nombreProducto;
    final TextView moneda;
    private final TextView precio;
    final LinearLayout secondLayout;
    final ImageButton agregar;
    final ImageButton quitar;

    RecyclerItemViewHolder(View viewBase) {
        super(viewBase);
        this.view = viewBase;
        this.cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        this.secondLayout = (LinearLayout) viewBase.findViewById(R.id.second_layout);
        this.agregar = (ImageButton) secondLayout.findViewById(R.id.agregar_imageview);
        this.quitar = (ImageButton) secondLayout.findViewById(R.id.quitar_imageview);
    }

    void llenarItem(Producto producto) {
        cantidad.setText(String.valueOf(producto.getCantidad()));
        nombreProducto.setText(producto.getNombre());
        precio.setText(String.format(Locale.getDefault(), "%.2f", producto.getPrecio()));

        if (producto.getCantidad() > 0) {
            cantidad.setVisibility(View.VISIBLE); //Se muestra el campo cantidad si es mayor que 0
            //view.setBackgroundColor(0x66FF7C00);
        } else {
            cantidad.setVisibility(View.GONE);
            //view.setBackgroundColor(0x00FFFFFF);
        }
    }
}