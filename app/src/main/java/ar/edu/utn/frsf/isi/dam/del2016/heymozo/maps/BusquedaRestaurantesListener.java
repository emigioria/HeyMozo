package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import java.util.List;

/**
 * Created by daniel on 23/01/17.
 */

public interface BusquedaRestaurantesListener<T> {
	void busquedaFinalizada(List<T> restaurantes, int resultCode);
	void busquedaIniciada();
}
