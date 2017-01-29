package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

/**
 * Created by lucas on 25/01/17.
 */

public class ViewHolderPedido {
    public TextView textviewPedido;
    public TextView textviewMoneda;
    public TextView textviewFecha;
    public TextView textviewEstado;
    public TextView textviewPrecio;
    public TextView textviewNombreRestaurante;
    public Button buttonGracias;
    public Button buttonEvaluarExp;

    public ViewHolderPedido(View viewBase){
        this.textviewPedido = (TextView) viewBase.findViewById(R.id.textviewPedido);
        this.textviewMoneda = (TextView) viewBase.findViewById(R.id.textviewMoneda);
        this.textviewFecha = (TextView) viewBase.findViewById(R.id.textviewFecha);
        this.textviewEstado = (TextView) viewBase.findViewById(R.id.textviewEstado);
        this.textviewPrecio = (TextView) viewBase.findViewById(R.id.textviewPrecio);
        this.textviewNombreRestaurante = (TextView) viewBase.findViewById(R.id.textviewNombreRestaurante);
        this.buttonGracias = (Button) viewBase.findViewById(R.id.buttonGracias);
        this.buttonEvaluarExp = (Button) viewBase.findViewById(R.id.buttonEvaluarExp);
    }
}
