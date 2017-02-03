package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class ViewHolderPedido {
    TextView textViewPedido;
    TextView textViewMoneda;
    TextView textViewFecha;
    TextView textViewEstado;
    TextView textViewTotal;
    TextView textViewNombreRestaurante;
    Button buttonGracias;
    Button buttonEvaluarExp;

    ViewHolderPedido(View viewBase) {
        this.textViewPedido = (TextView) viewBase.findViewById(R.id.textViewPedido);
        this.textViewMoneda = (TextView) viewBase.findViewById(R.id.textViewMoneda);
        this.textViewFecha = (TextView) viewBase.findViewById(R.id.textViewFecha);
        this.textViewEstado = (TextView) viewBase.findViewById(R.id.textViewEstado);
        this.textViewTotal = (TextView) viewBase.findViewById(R.id.textViewTotal);
        this.textViewNombreRestaurante = (TextView) viewBase.findViewById(R.id.textViewNombreRestaurante);
        this.buttonGracias = (Button) viewBase.findViewById(R.id.buttonGracias);
        this.buttonEvaluarExp = (Button) viewBase.findViewById(R.id.buttonEvaluarExp);
    }
}
