package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Carta {
    private static final String RESTAURANT = "nombre_restaurant";
    private static final String SECCIONES = "secciones";

    @SerializedName(RESTAURANT)
    private Restaurante restaurante;

    @SerializedName(SECCIONES)
    private ArrayList<Seccion> secciones = new ArrayList<>();

    public Carta() {

    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public ArrayList<Seccion> getSecciones() {
        return secciones;
    }

    public Carta setSecciones(ArrayList<Seccion> secciones) {
        this.secciones = secciones;
        return this;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public Carta setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        return this;
    }
}
