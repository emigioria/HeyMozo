package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

public class Pedido {
    private String nombreRestaurant;
    private String codigoMesa;
    private ArrayList<Producto> productos;
    private Long finaliza;
    private String estado;
    private String moneda;
    private byte[] imagenRestaurante;

    public Pedido() {
        productos = new ArrayList<>();
    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public Pedido setNombreRestaurant(String nombreRestaurant) {
        this.nombreRestaurant = nombreRestaurant;
        return this;
    }

    public String getNombreRestaurant() {
        return nombreRestaurant;
    }

    public Pedido setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
        return this;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public Pedido setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Pedido setFinaliza(Date finaliza) {
        if(finaliza!=null){
            this.finaliza = finaliza.getTime();
        }
        return this;
    }

    public Pedido setFinaliza(Long finaliza) {
        this.finaliza = finaliza;
        return this;
    }

    public Long getFinaliza() {
        return finaliza;
    }

    public Date getFinalizaDate() {
        if(finaliza==null){
            return null;
        }
        return new Date(finaliza);
    }

    public Pedido setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Pedido setMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

    public String getMoneda() {
        return moneda;
    }

    public byte[] getImagenRestaurante() {
        return imagenRestaurante;
    }

    public void setImagenRestaurante(byte[] imagenRestaurante) {
        this.imagenRestaurante = imagenRestaurante;
    }
}
