package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Mesa;

public class SolicitarCartaTask extends AsyncTask<String, Void, String> {
    private final Context context;
    private SolicitarCartaListener listener;
    private HttpURLConnection urlConnection = null;
    public static final int OK = 0;
    public static final int CANCELADO = 1;
    public static final int ERROR = 2;
    private int status = OK;

    private String mesaJSON;

    public SolicitarCartaTask(SolicitarCartaListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.busquedaIniciada();
    }

    @Override
    protected String doInBackground(String... ids) {
        String cartaJSON = null;
        String idRestaurante = ids[0];
        mesaJSON = new Gson().toJson(new Mesa().setId(ids[1]));
        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/cartas/" + idRestaurante);
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
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
            isw.close();
            in.close();
            cartaJSON = sb.toString();
        } catch (IOException e) {
            status = ERROR;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return cartaJSON;
    }

    @Override
    protected void onPostExecute(String cartaJSON) {
        if (isCancelled()) {
            status = CANCELADO;
        }
        listener.busquedaFinalizada(cartaJSON, mesaJSON, status);
    }
}
