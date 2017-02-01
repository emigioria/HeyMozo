package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import com.google.gson.annotations.SerializedName;

public class Restaurante {
    private static final String NOMBRE = "nombre";
    private static final String LATITUD = "latitud";
    private static final String LONGITUD = "longitud";

    @SerializedName(NOMBRE)
    private String nombre;

    @SerializedName(LATITUD)
    private Double latitud;

    @SerializedName(LONGITUD)
    private Double longitud;

    public Restaurante() {

    }

    public String getNombre() {
        return nombre;
    }

    public Restaurante setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Restaurante setLatitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Restaurante setLongitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }
}
