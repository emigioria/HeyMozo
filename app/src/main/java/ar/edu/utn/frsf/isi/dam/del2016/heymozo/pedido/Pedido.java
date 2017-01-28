package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

class Pedido {
    //public static final String ID = "id";
    private static final String NOMBRE_RESTAURANT = "nombre_restaurant";
    private static final String MESA = "codigo_mesa";
    private static final String CODIGO_MESA = "codigo_mesa";
    private static final String PRODUCTOS = "productos";
    private static final String FIN_ESPERA = "finaliza";

    private String nombreRestaurant;
    private String codigoMesa;
    private ArrayList<Producto> productos;
    private Date finaliza;
    private String estado;

    Pedido(){
        productos = new ArrayList<>();
    }

    Pedido setNombreRestaurant(String nombreRestaurant){
        this.nombreRestaurant = nombreRestaurant;
        return this;
    }

    Pedido setCodigoMesa(String codigoMesa){
        this.codigoMesa = codigoMesa;
        return this;
    }

    Pedido setProductos(ArrayList<Producto> productos){
        this.productos = productos;
        return this;
    }

    Pedido setEstado(String estado){
        this.estado = estado;
        return this;
    }

    Pedido setFinaliza(Date fin){
        this.finaliza = fin;
        return this;
    }

    Pedido(String pedidoString) throws JSONException {
        JSONObject pedidoJSON = new JSONObject(pedidoString);
        this.nombreRestaurant = pedidoJSON.getJSONObject(NOMBRE_RESTAURANT).getString(NOMBRE_RESTAURANT);
        try {
            this.codigoMesa = pedidoJSON.getJSONObject(MESA).getString(CODIGO_MESA);
        }
        catch (JSONException e){
            this.codigoMesa = null;
        }

        //inicializar lista de titulos de secciones
        JSONArray jsonArrayProductos = (JSONArray) pedidoJSON.get(PRODUCTOS);
        if (jsonArrayProductos != null) {
            for (int i = 0; i < jsonArrayProductos.length(); i++) {
                productos.add(
                        new Producto(jsonArrayProductos.getJSONObject(i))
                );
            }
        }
    }

    String getNombreRestaurant() {
        return nombreRestaurant;
    }

    String getCodigoMesa(){
        return codigoMesa;
    }

    ArrayList<Producto> getProductos(int seccionID) throws JSONException {
        return productos;
    }

    String getEstado(){
        return estado;
    }

    Date getFinaliza(){
        return finaliza;
    }
}
