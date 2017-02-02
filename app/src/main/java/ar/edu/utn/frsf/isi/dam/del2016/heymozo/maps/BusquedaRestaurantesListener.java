package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import java.util.List;

interface BusquedaRestaurantesListener<T> {
	void busquedaFinalizada(List<T> restaurantes, int resultCode);
	void busquedaIniciada();
}
