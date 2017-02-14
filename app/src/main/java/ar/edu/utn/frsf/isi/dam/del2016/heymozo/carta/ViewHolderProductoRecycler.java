package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class ViewHolderProductoRecycler extends RecyclerView.ViewHolder {

    final View view;
    final TextView cantidad;
    final TextView nombreProducto;
    final TextView moneda;
    final TextView precio;
    final LinearLayout secondLayout;
    final TextView descripcionCorta;
    final ImageButton agregar;
    final ImageButton quitar;
    final ImageView imagenProducto;
    final ImageView imagenMasInformacion;
    final ProgressBar progressBar;
    final TextView mensajeAyudaInformacion;

    ViewHolderProductoRecycler(View viewBase) {
        super(viewBase);
        this.view = viewBase;
        this.cantidad = (TextView) viewBase.findViewById(R.id.cantidad_textview);
        this.nombreProducto = (TextView) viewBase.findViewById(R.id.nombre_producto_textview);
        this.moneda = (TextView) viewBase.findViewById(R.id.moneda_textview);
        this.precio = (TextView) viewBase.findViewById(R.id.precio_textview);
        this.secondLayout = (LinearLayout) viewBase.findViewById(R.id.second_layout);
        this.descripcionCorta = (TextView) viewBase.findViewById(R.id.descripcion_textview);
        this.agregar = (ImageButton) secondLayout.findViewById(R.id.agregar_imageview);
        this.quitar = (ImageButton) secondLayout.findViewById(R.id.quitar_imageview);
        this.imagenProducto = (ImageView) viewBase.findViewById(R.id.imagen_producto);
        this.imagenMasInformacion = (ImageView) viewBase.findViewById(R.id.mas_informacion_imageview);
        this.progressBar = (ProgressBar) viewBase.findViewById(R.id.progressBar);
        this.mensajeAyudaInformacion = (TextView) viewBase.findViewById(R.id.ayuda_item_informacion_mensaje);
    }
}