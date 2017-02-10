package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.view.View;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

interface MostrarCartaListener {
    void mostrarCarta(Restaurante restaurante, View card);
}
