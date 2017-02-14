package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Carta;

interface CartaListener {
    Carta getCarta();

    Boolean getNoHacerPedidos();

    FloatingActionButton getFab();

    LinearLayout getMensajeAyudaRealizarPedido();
}
