package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

public interface BusquedaRestaurantesListener {
	void busquedaRestauranteFinalizada(List<Restaurante> restaurantes, int resultCode);

	void busquedaRestauranteIniciada();
}
