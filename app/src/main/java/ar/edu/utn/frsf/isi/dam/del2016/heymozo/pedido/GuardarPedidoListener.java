package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

public interface GuardarPedidoListener {
    void guardadoFinalizado(int resultCode);

    void guardadoIniciado();
}
