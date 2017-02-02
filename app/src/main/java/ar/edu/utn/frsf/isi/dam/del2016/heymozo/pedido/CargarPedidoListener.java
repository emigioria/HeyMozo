package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

interface CargarPedidoListener {
    void cargaFinalizada(Pedido pedido, int resultCode);

    void cargaIniciada();
}
