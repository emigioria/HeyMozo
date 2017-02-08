package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

public class Imagen {
    private static final String IMAGEN_ID = "id";
    private static final String urlBaseImagenes = "/imagenes/";

    @SerializedName(IMAGEN_ID)
    private String imagenId;

    public String getUrlImagen(Context context) {
        if (imagenId == null) {
            return null;
        }
        return "http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + urlBaseImagenes + imagenId;
    }
}
