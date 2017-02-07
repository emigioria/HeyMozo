package ar.edu.utn.frsf.isi.dam.del2016.heymozo.imagenes;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

public interface CargarImagenListener {
    void imagenCargada(Producto producto, int posicion, int status);
}
