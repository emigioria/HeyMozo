package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.annotations.SerializedName;

public class Imagen {
    private static final String IMAGEN_ID = "imagen_id";
    private static final String IMAGEN = "imagen";

    @SerializedName(IMAGEN_ID)
    private MongoId imagenId;

    @SerializedName(IMAGEN)
    private transient byte[] imagen;

    public String getImagenId() {
        return imagenId.getId();
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Imagen setImagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }
}
