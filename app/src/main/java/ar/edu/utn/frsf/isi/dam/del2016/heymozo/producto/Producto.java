package ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Producto {
    private static final String CANTIDAD = "cantidad";
    private static final String NOMBRE_PRODUCTO = "nombre";
    private static final String MONEDA = "moneda";
    private static final String PRECIO = "precio";

    @SerializedName(CANTIDAD)
    private Integer cantidad;

    @SerializedName(NOMBRE_PRODUCTO)
    private String nombre;

    @SerializedName(MONEDA)
    private Moneda moneda;

    @SerializedName(PRECIO)
    private Double precio;

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

    public Double getPrecio() {
        return precio;
    }

    public Producto setPrecio(Double precio) {
        this.precio = precio;
        return this;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public Producto setMoneda(Moneda moneda) {
        this.moneda = moneda;
        return this;
    }
}
