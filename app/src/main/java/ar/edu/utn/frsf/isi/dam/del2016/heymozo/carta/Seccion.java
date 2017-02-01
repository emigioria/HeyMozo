package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

public class Seccion {
    private static final String NOMBRE = "nombre";
    private static final String PRODUCTOS = "productos";

    @SerializedName(NOMBRE)
    private String nombre;

    @SerializedName(PRODUCTOS)
    private ArrayList<Producto> productos = new ArrayList<>();

    public Seccion() {

    }

    public String getNombre() {
        return nombre;
    }

    public Seccion setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Seccion setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
        return this;
    }
}
