package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.annotations.SerializedName;

class MongoId {
    private static final String ID = "$oid";

    @SerializedName(ID)
    private String id;

    public String getId() {
        return id;
    }
}
