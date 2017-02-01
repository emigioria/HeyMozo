package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

/**
 * Created by lucas on 31/01/17.
 */
public interface SolicitarCartaListener {
    void busquedaIniciada();
    void busquedaFinalizada(String cartaJSON, int status);
}
