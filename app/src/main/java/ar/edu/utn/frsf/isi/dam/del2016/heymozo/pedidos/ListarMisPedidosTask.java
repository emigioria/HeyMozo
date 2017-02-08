package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

class ListarMisPedidosTask extends AsyncTask<Void, Void, List<Pedido>> {
    private final Context context;
    private ListarMisPedidosListener listener;
    static final int OK = 0;
    static final int CANCELADO = 1;
    static final int ERROR = 2;
    private int status = OK;

    ListarMisPedidosTask(ListarMisPedidosListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.busquedaIniciada();
    }

    @Override
    protected void onPostExecute(List<Pedido> pedidos) {
        if (isCancelled()) {
            status = CANCELADO;
        }
        listener.busquedaFinalizada(pedidos, status);
    }

    @Override
    protected List<Pedido> doInBackground(Void... urls) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("registration_id", "no id");
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/pedidos/" + userId);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
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
            // create the type for the collection. In this case define that the collection is of type Dataset
            Type datasetListType = new TypeToken<Collection<Pedido>>() {
            }.getType();
            pedidos = new Gson().fromJson(sb.toString(), datasetListType);
        } catch (Exception e) {
            status = ERROR;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return pedidos;
    }
}
