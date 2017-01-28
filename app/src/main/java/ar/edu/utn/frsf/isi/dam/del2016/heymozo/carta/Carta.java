package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;

/**
 * Created by lucas on 25/01/17.
 */

class Carta {
    //public static final String ID = "id";
    private static final String NOMBRE_RESTAURANT = "nombre_restaurant";
    private static final String SECCIONES = "secciones";
    private static final String NOMBRE_SECCION = "nombre";

    private String nombreRestaurant;
    private ArrayList<String> secciones;
    private ArrayList<ArrayList<Producto>> productosSecciones;

    Carta(String cartaString) throws JSONException {
        JSONObject cartaJSON = new JSONObject(cartaString);
        this.nombreRestaurant = cartaJSON.getJSONObject(NOMBRE_RESTAURANT).getString(NOMBRE_RESTAURANT);
        secciones =  new ArrayList<>();
        productosSecciones = new ArrayList<>();

        //inicializar lista de titulos de secciones
        JSONArray jsonArrayAux = (JSONArray) cartaJSON.get(SECCIONES);
        if (jsonArrayAux != null) {
            for (int i = 0; i < jsonArrayAux.length(); i++) {
                String tituloSeccion = jsonArrayAux.getJSONObject(i).getString(NOMBRE_SECCION);
                secciones.add(tituloSeccion);

                //Por cada seccion, busca los productos correspondientes a la misma e inicializa la lista de productos
                ArrayList<Producto> productosSeccion = new ArrayList<>();
                JSONArray productosSeccionJSON = cartaJSON.getJSONArray(tituloSeccion);
                for (int j = 0; j < productosSeccionJSON.length(); j++) {
                    productosSeccion.add(
                            new Producto(productosSeccionJSON.getJSONObject(j))
                    );
                }
                productosSecciones.add(i,productosSeccion);
            }
        }
    }

    ArrayList<String> getSecciones() {
        return secciones;
    }

    ArrayList<Producto> getProductosSeccion(int seccionID) throws JSONException {
        return productosSecciones.get(seccionID);
    }

    String getNombreRestaurant(){
        return nombreRestaurant;
    }
}
