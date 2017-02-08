package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;

class CargarPedidoTask extends AsyncTask<String, Void, Pedido> {
    private final Context context;
    private CargarPedidoListener listener;
    static final int OK = 0;
    static final int CANCELADO = 1;
    static final int ERROR = 2;
    private int status = OK;

    CargarPedidoTask(CargarPedidoListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.cargaIniciada();
    }

    @Override
    protected void onPostExecute(Pedido pedido) {
        if (isCancelled()) {
            status = CANCELADO;
        }
        listener.cargaFinalizada(pedido, status);
    }

    @Override
    protected Pedido doInBackground(String... pedidosId) {
        if (pedidosId.length < 1) {
            return null;
        }
        String pedidoId = pedidosId[0];
        if (pedidoId == null) {
            return null;
        }

        Pedido pedido = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("registration_id", "no id");
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/pedidos/" + userId + "/" + pedidoId);
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
            String pedidoJSON = sb.toString();
            pedido = new Gson().fromJson(pedidoJSON, Pedido.class);
        } catch (Exception e) {
            status = ERROR;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return pedido;
    }
}
