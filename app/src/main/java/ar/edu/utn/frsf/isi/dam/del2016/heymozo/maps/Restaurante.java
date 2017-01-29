package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

/**
 * Created by daniel on 23/01/17.
 */
public class Restaurante {
	private Integer id;
	private String nombre;
	private Double latitud;
	private Double longitud;

	public Restaurante(Integer id, String nombre, Double latitud, Double longitud) {
		this.id = id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Restaurante(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id =id;
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
