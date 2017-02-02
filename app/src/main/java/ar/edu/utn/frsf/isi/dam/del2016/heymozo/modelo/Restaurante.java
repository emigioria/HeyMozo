package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Restaurante {
    private static final String NOMBRE = "nombre";
    private static final String LATITUD = "latitud";
    private static final String LONGITUD = "longitud";
    private static final String IMAGEN_RESTAURANTE = "imagen";

    @SerializedName(NOMBRE)
    private String nombre;

    @SerializedName(LATITUD)
    private Double latitud;

    @SerializedName(LONGITUD)
    private Double longitud;

    @SerializedName(IMAGEN_RESTAURANTE)
    private byte[] imagen;

    public Restaurante() {

    }

    public String toJSONObject() {
        return new Gson().toJson(this);
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

    public byte[] getImagen() {
        return imagen;
    }

    public Restaurante setImagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

}
