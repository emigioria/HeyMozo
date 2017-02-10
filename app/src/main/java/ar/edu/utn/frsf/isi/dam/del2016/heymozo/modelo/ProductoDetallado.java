package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ProductoDetallado extends Producto {
    private static final String IMAGEN = "imagen";
    private static final String DESCRIPCION_CORTA = "descripcion_corta";
    private static final String DESCRIPCION_LARGA = "descripcion_larga";

    @SerializedName(IMAGEN)
    private Imagen imagen;

    @SerializedName(DESCRIPCION_CORTA)
    private String descripcion;

    @SerializedName(DESCRIPCION_LARGA)
    private String descripcionLarga;

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public Imagen getImagen() {
        return imagen;
    }

    public Producto setImagen(Imagen imagen) {
        this.imagen = imagen;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ProductoDetallado setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public ProductoDetallado setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }
}
