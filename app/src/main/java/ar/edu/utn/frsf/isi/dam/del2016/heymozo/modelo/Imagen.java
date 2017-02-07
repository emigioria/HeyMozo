package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Imagen {
    private static final String IMAGEN_ID = "id";
    private static final String IMAGEN64 = "imagen64";

    @SerializedName(IMAGEN_ID)
    private String imagenId;

    @SerializedName(IMAGEN64)
    private String imagen64;

    private Bitmap imagen;

    public String getImagenId() {
        return imagenId;
    }

    public Imagen setImagenId(String imagenId) {
        this.imagenId = imagenId;
        return this;
    }

    public String getImagen64() {
        return imagen64;
    }

    public Imagen setImagen64(String imagen64) {
        this.imagen64 = imagen64;
        return this;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public Imagen setImagen(Bitmap imagen) {
        this.imagen = imagen;
        return this;
    }
}
