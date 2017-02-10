package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class ViewHolderRestaurante extends RecyclerView.ViewHolder {

    final View row;
    final ImageView imageViewFotoRestaurante;
    final TextView textViewNombreRestaurante;
    final TextView textViewDireccion;
    final TextView textViewTelefono;
    final TextView textViewPaginaWeb;
    final RatingBar ratingBar;
    final Button buttonVerCarta;

    ViewHolderRestaurante(View viewBase) {
        super(viewBase);
        this.row = viewBase;
        this.imageViewFotoRestaurante = (ImageView) viewBase.findViewById(R.id.imageViewFotoRestaurante);
        this.textViewNombreRestaurante = (TextView) viewBase.findViewById(R.id.textViewNombreRestaurante);
        this.textViewDireccion = (TextView) viewBase.findViewById(R.id.textViewDireccion);
        this.textViewTelefono = (TextView) viewBase.findViewById(R.id.textViewTelefono);
        this.textViewPaginaWeb = (TextView) viewBase.findViewById(R.id.textViewPaginaWeb);
        this.ratingBar = (RatingBar) viewBase.findViewById(R.id.ratingBar);
        this.buttonVerCarta = (Button) viewBase.findViewById(R.id.buttonVerCarta);
    }
}
