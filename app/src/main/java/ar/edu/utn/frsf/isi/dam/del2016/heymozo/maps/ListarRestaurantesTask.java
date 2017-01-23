package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.Restaurante;

/**
 * Created by daniel on 23/01/17.
 */
public class ListarRestaurantesTask extends AsyncTask<String, Void, List<Restaurante>>{
	private BusquedaRestaurantesListener<Restaurante> listener;
	private static String IP_SERVER = "192.168.1.103";
	private static String PORT_SERVER = "3000";

	public ListarRestaurantesTask(BusquedaRestaurantesListener<Restaurante> dListener){
		this.listener = dListener;
	}

	@Override
	protected  void onPreExecute(){
		listener.busquedaIniciada() ;
	}

	@Override
	protected void onPostExecute(List<Restaurante> restaurantes) {
		listener.busquedaFinalizada(restaurantes);
	}

	@Override
	protected List<Restaurante> doInBackground(String... urls) {
		final StringBuilder sb = new StringBuilder();
		ArrayList<Restaurante> restaurantes = new ArrayList<>();
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/restaurantes/");
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			InputStreamReader isw = new InputStreamReader(in);
			int data = isw.read();
			while (data != -1) {
				char current = (char) data;
				sb.append(current);
				data = isw.read();
			}
			Log.d("JSON", "doInBackground: "+ sb.toString());
			Gson gson = new Gson();
			// create the type for the collection. In this case define that the collection is of type Dataset
			Type datasetListType = new TypeToken<Collection<Restaurante>>() {}.getType();
			restaurantes = gson.fromJson(sb.toString(), datasetListType);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) urlConnection.disconnect();
		}
		return restaurantes;
	}
}
