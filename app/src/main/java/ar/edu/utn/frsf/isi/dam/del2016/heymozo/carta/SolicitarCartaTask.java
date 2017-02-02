package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

public class SolicitarCartaTask extends AsyncTask<Object, Object, String> {
    private final Context context;
    private SolicitarCartaListener listener;
    private HttpURLConnection urlConnection = null;
    public static final int OK = 0;
    public static final int CANCELADO = 1;
    public static final int ERROR = 2;
    private int status = OK;

    private Integer idRestaurante;
    private Integer idMesa;

    public SolicitarCartaTask(SolicitarCartaListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected  void onPreExecute(){
        listener.busquedaIniciada();
    }

    @Override
    protected String doInBackground(Object... ids) {
        String cartaJSON = null;
        idRestaurante = Integer.valueOf(ids[0].toString());
        idMesa = Integer.valueOf(ids[1].toString());
        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/cartas/" + idRestaurante);
            Log.v("INFO:", url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
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
        if(isCancelled()){
            status = CANCELADO;
        }
        listener.busquedaFinalizada(cartaJSON, idMesa, status);
    }
}
