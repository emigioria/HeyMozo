package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lucas on 25/01/17.
 */

class Carta {
    private String nombreRestaurant;
    private String moneda;
    private ArrayList<String> secciones;
    private ArrayList<ArrayList<Producto>> productosSecciones;

    //Metadata
    private static final String NOMBRE_RESTAURANT = "nombre_restaurant";
    private static final String MONEDA = "moneda";
    private static final String SECCIONES = "secciones";
    //public static final String ID = "id";
    private static final String NOMBRE_SECCION = "nombre";
    private static final String NOMBRE_PRODUCTO = "nombre";
    private static final String PRECIO = "precio";

    Carta(String cartaString) throws JSONException {
        JSONObject cartaJSON = new JSONObject(cartaString);
        this.nombreRestaurant = cartaJSON.getJSONObject(NOMBRE_RESTAURANT).getString(NOMBRE_RESTAURANT);
        this.moneda = cartaJSON.getJSONObject(MONEDA).getString(MONEDA);
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
                            .setCarta(this)
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

    String getMoneda(){
        return moneda;
    }

    class Producto {
        private String nombre;
        private Double precio;
        private Carta carta;

        Producto(JSONObject itemJSON) throws JSONException {
            this.nombre = itemJSON.getString(NOMBRE_PRODUCTO);
            this.precio = itemJSON.getDouble(PRECIO);
        }

        public Producto setCarta(Carta carta){
            this.carta = carta;
            return this;
        }

        String getNombre(){
            return nombre;
        }

        Double getPrecio(){
            return precio;
        }

        public Carta getCarta(){
            return carta;
        }
    }
}
