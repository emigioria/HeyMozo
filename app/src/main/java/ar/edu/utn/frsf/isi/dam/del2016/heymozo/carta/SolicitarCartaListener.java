package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

public interface SolicitarCartaListener {
    void busquedaIniciada();
    void busquedaFinalizada(String cartaJSON, Integer idMesa, int status);
}
