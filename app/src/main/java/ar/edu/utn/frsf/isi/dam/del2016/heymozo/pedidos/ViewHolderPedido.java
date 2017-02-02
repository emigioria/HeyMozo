package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class ViewHolderPedido {
    TextView textviewPedido;
    TextView textviewMoneda;
    TextView textviewFecha;
    TextView textviewEstado;
    TextView textviewTotal;
    TextView textviewNombreRestaurante;
    Button buttonGracias;
    Button buttonEvaluarExp;

    ViewHolderPedido(View viewBase) {
        this.textviewPedido = (TextView) viewBase.findViewById(R.id.textviewPedido);
        this.textviewMoneda = (TextView) viewBase.findViewById(R.id.textviewMoneda);
        this.textviewFecha = (TextView) viewBase.findViewById(R.id.textviewFecha);
        this.textviewEstado = (TextView) viewBase.findViewById(R.id.textviewEstado);
        this.textviewTotal = (TextView) viewBase.findViewById(R.id.textViewTotal);
        this.textviewNombreRestaurante = (TextView) viewBase.findViewById(R.id.textviewNombreRestaurante);
        this.buttonGracias = (Button) viewBase.findViewById(R.id.buttonGracias);
        this.buttonEvaluarExp = (Button) viewBase.findViewById(R.id.buttonEvaluarExp);
    }
}
