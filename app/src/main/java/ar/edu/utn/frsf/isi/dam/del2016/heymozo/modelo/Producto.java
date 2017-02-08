package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Producto implements Serializable {
    private static final String CANTIDAD = "cantidad";
    private static final String NOMBRE_PRODUCTO = "nombre";
    private static final String IMAGEN = "imagen";
    private static final String PRECIO = "precio";
    private static final String DESCRIPCION = "descripcion";
    private static final String DESCRIPCION_LARGA = "descripcion_larga";

    @SerializedName(CANTIDAD)
    private Integer cantidad;

    @SerializedName(NOMBRE_PRODUCTO)
    private String nombre;

    @SerializedName(IMAGEN)
    private Imagen imagen;

    @SerializedName(PRECIO)
    private Double precio;

    @SerializedName(DESCRIPCION)
    private String descripcion;

    @SerializedName(DESCRIPCION_LARGA)
    private String descripcionLarga;

    public Producto() {
        cantidad = 0;
    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Producto setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public Producto setImagen(Imagen imagen) {
        this.imagen = imagen;
        return this;
    }

    public Double getPrecio() {
        return precio;
    }

    public Producto setPrecio(Double precio) {
        this.precio = precio;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public Producto setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }
}
