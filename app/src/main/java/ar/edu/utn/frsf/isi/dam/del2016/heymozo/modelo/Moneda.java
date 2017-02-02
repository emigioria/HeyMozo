package ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Moneda {
    private static final String SIMBOLO = "moneda";

    @SerializedName(SIMBOLO)
    private String simbolo;

    public Moneda() {

    }

    public String toJSONObject() {
        return new Gson().toJson(this);
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Moneda setSimbolo(String simbolo) {
        this.simbolo = simbolo;
        return this;
    }
}