package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Mesa {
    private static final String ID = "id";

    @SerializedName(ID)
    private String id;

    public Mesa() {

    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public String getId() {
        return id;
    }

    public Mesa setId(String id) {
        this.id = id;
        return this;
    }
}
