package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

/**
 * Created by daniel on 23/01/17.
 */
public class Restaurante {
	private Integer _id;
	private String nombre;
	private Double latitud;
	private Double longitud;

	public Restaurante(Integer _id, String nombre, Double latitud, Double longitud) {
		this._id = _id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Restaurante(Integer _id) {
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
}
