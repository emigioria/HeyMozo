package ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Emiliano on 27/1/2017.
 */

public class Producto {
    private static final String CANTIDAD = "cantidad";
    private static final String NOMBRE_PRODUCTO = "nombre";
    private static final String MONEDA = "moneda";
    private static final String PRECIO = "precio";

    private Integer cantidad;
    private String nombre;
    private String moneda;
    private Double precio;

    public Producto(JSONObject itemJSON) throws JSONException {
        this.nombre = itemJSON.getString(NOMBRE_PRODUCTO);
        this.precio = itemJSON.getDouble(PRECIO);
        this.moneda = itemJSON.getJSONObject(MONEDA).getString(MONEDA);
        try {
            this.cantidad = itemJSON.getInt(CANTIDAD);
        }
        catch (JSONException e) {
            this.cantidad = 0;
        }
    }

    public Producto setCantidad(Integer cantidad){
        this.cantidad = cantidad;
        return this;
    }

    public Integer getCantidad(){
        return cantidad;
    }

    public String getNombre(){
        return nombre;
    }

    public Double getPrecio(){
        return precio;
    }

    public String getMoneda(){
        return moneda;
    }
}
