package ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

public class ListarRestaurantesTask extends AsyncTask<Void, Void, List<Restaurante>> {
    private final Context context;
    private BusquedaRestaurantesListener listener;
    private HttpURLConnection urlConnection = null;
    private int status = OK;
    public static final int OK = 0;
    public static final int CANCELADO = 1;
    public static final int ERROR = 2;

    public ListarRestaurantesTask(BusquedaRestaurantesListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.busquedaRestauranteIniciada();
    }

    @Override
    protected void onPostExecute(List<Restaurante> restaurantes) {
        if (isCancelled()) {
            status = CANCELADO;
        }
        listener.busquedaRestauranteFinalizada(restaurantes, status);
    }

    @Override
    protected List<Restaurante> doInBackground(Void... nadas) {
        ArrayList<Restaurante> restaurantes = new ArrayList<>();
        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/restaurantes");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            while (data != -1) {
                if (isCancelled())
                    return null;
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
            isw.close();
            in.close();
            Gson gson = new Gson();
            // create the type for the collection. In this case define that the collection is of type Dataset
            Type datasetListType = new TypeToken<Collection<Restaurante>>() {
            }.getType();
            restaurantes = gson.fromJson(sb.toString(), datasetListType);
        } catch (IOException e) {
            status = ERROR;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return restaurantes;
    }
}
