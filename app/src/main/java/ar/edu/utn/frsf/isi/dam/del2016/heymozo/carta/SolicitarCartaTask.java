package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lucas on 31/01/17.
 */

public class SolicitarCartaTask extends AsyncTask<Object, Object, String> {
    private SolicitarCartaListener listener;
    private static final String IP_SERVER = "192.168.0.10";
    private static final String PORT_SERVER = "3000";
    private HttpURLConnection urlConnection = null;
    public static final int OK = 0;
    public static final int CANCELADO = 1;
    public static final int ERROR = 2;
    private int status = OK;

    public SolicitarCartaTask(SolicitarCartaListener dListener){
        this.listener = dListener;
    }

    @Override
    protected  void onPreExecute(){
        listener.busquedaIniciada() ;
    }

    @Override
    protected String doInBackground(Object... mesa) {
        StringBuilder sb = null;
        try {
            URL url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/cartas/" + mesa[0] + "/");
            Log.v("INFO:",url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            sb = new StringBuilder();
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            return sb.toString();
        }
    }

    @Override
    protected void onPostExecute(String cartaJSON) {
        if(isCancelled()){
            status = CANCELADO;
        }
        listener.busquedaFinalizada(cartaJSON, status);
    }
}
