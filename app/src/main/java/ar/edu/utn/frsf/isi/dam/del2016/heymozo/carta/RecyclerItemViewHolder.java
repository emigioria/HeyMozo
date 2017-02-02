package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

import static java.lang.String.valueOf;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    final TextView cantidad;
    private final TextView nombreProducto;
    public final TextView moneda;
    private final TextView precio;
    final LinearLayout secondLayout;
    final ImageView agregar;
    final ImageView quitar;

    public RecyclerItemViewHolder(final View parent, TextView cantidad, TextView nombreProducto, TextView moneda, TextView precio, LinearLayout secondLayout, ImageView agregar, ImageView quitar) {
        super(parent);
        this.view = parent;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.moneda = moneda;
        this.precio = precio;
        this.secondLayout = secondLayout;
        this.agregar = agregar;
        this.quitar = quitar;
    }

    public static RecyclerItemViewHolder newInstance(View viewBase) {
        TextView cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        TextView nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        TextView moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        TextView precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        LinearLayout secondLayout = (LinearLayout) viewBase.findViewById(R.id.second_layout);
        ImageView agregar = (ImageView) secondLayout.findViewById(R.id.agregar_imageview);
        ImageView quitar = (ImageView) secondLayout.findViewById(R.id.quitar_imageview);

        return new RecyclerItemViewHolder(viewBase, cantidad, nombreProducto, moneda, precio, secondLayout, agregar, quitar);
    }

    public void llenarItem(Producto producto) {

        cantidad.setText(String.valueOf(producto.getCantidad()));
        nombreProducto.setText(producto.getNombre());
        moneda.setText(producto.getMoneda().getSimbolo());
        precio.setText(String.format(Locale.getDefault(),"%.2f",producto.getPrecio()));

        if(producto.getCantidad()>0){
            cantidad.setVisibility(View.VISIBLE); //Se muestra el campo cantidad si es mayor que 0
            view.setBackgroundColor(0x66FF7C00);
        }else{
            cantidad.setVisibility(View.GONE);
            view.setBackgroundColor(0x00FFFFFF);
        }



    }

}