package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import com.google.gson.annotations.SerializedName;

public class Mesa {
    private static final String ID = "id";

    @SerializedName(ID)
    private Integer id;

    public Mesa() {

    }

    public Integer getId() {
        return id;
    }

    public Mesa setId(Integer id) {
        this.id = id;
        return this;
    }
}
