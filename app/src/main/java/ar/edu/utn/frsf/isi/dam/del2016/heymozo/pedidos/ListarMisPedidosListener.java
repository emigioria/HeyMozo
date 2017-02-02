package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.Pedido;

public interface ListarMisPedidosListener {
    void busquedaFinalizada(List<Pedido> pedidos, int resultCode);

    void busquedaIniciada();
}
