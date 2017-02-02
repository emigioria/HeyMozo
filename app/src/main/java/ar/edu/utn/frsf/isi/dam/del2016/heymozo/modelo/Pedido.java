package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    private static final String USUARIO = "usuario_id";
    private static final String RESTAURANTE = "nombre_restaurant";
    private static final String MESA = "codigo_mesa";
    private static final String PRODUCTOS = "productos";
    private static final String FINALIZA = "finaliza";
    private static final String ESTADO = "estado";
    private static final String MONEDA = "moneda";
    private static final String FECHA_PEDIDO = "fecha_pedido";
    private static final String TOTAL = "total";
    private static final String CALIFICACION = "calificacion";

    @SerializedName(USUARIO)
    private String userID;

    @SerializedName(RESTAURANTE)
    private Restaurante restaurante;

    @SerializedName(MESA)
    private Mesa mesa;

    @SerializedName(PRODUCTOS)
    private ArrayList<Producto> productos = new ArrayList<>();

    @SerializedName(FINALIZA)
    private Long finaliza;

    @SerializedName(ESTADO)
    private String estado;

    @SerializedName(MONEDA)
    private Moneda moneda;

    @SerializedName(FECHA_PEDIDO)
    private Long fechaPedido;

    @SerializedName(TOTAL)
    private Double total;

    @SerializedName(CALIFICACION)
    private String calificacion;

    public Pedido() {

    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public String getUserID() {
        return userID;
    }

    public Pedido setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public Pedido setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        return this;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public Pedido setMesa(Mesa mesa) {
        this.mesa = mesa;
        return this;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Pedido setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Long getFinaliza() {
        return finaliza;
    }

    public Date getFinalizaDate() {
        if (finaliza == null) {
            return null;
        }
        return new Date(finaliza);
    }

    public Pedido setFinaliza(Date finaliza) {
        if (finaliza != null) {
            this.finaliza = finaliza.getTime();
        }
        return this;
    }

    public Pedido setFinaliza(Long finaliza) {
        this.finaliza = finaliza;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Pedido setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public Pedido setMoneda(Moneda moneda) {
        this.moneda = moneda;
        return this;
    }

    public Double getTotal() {
        if (this.total != null) {
            return this.total;
        }
        Double total = 0.0;
        for (Producto p : this.getProductos()) {
            total += p.getPrecio() * p.getCantidad();
        }
        this.total = total;
        return total;
    }

    public Pedido setTotal(Double total) {
        this.total = total;
        return this;
    }

    public Date getFechaPedidoDate() {
        if (fechaPedido == null) {
            return null;
        }
        return new Date(fechaPedido);
    }

    public Long getFechaPedido() {
        return fechaPedido;
    }

    public Pedido setFechaPedido(Date fechaPedido) {
        if (fechaPedido != null) {
            this.fechaPedido = fechaPedido.getTime();
        }
        return this;
    }

    public Pedido setFechaPedido(Long fechaPedido) {
        this.fechaPedido = fechaPedido;
        return this;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public Pedido setCalificacion(String calificacion) {
        this.calificacion = calificacion;
        return this;
    }
}
