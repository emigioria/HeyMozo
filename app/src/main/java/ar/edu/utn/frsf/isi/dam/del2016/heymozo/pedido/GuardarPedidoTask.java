package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

public class GuardarPedidoTask extends AsyncTask<Pedido, Void, Void> {
    private final Context context;
    private GuardarPedidoListener listener;
    private HttpURLConnection urlConnection = null;
    static final int OK = 0;
    static final int CANCELADO = 1;
    static final int ERROR = 2;
    private int status = OK;

    public GuardarPedidoTask(GuardarPedidoListener dListener, Context context) {
        this.listener = dListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.guardadoIniciado();
    }

    @Override
    protected void onPostExecute(Void nada) {
        if (isCancelled()) {
            status = CANCELADO;
        }
        listener.guardadoFinalizado(status);
    }

    @Override
    protected Void doInBackground(Pedido... pedidos) {
        if (pedidos.length < 1) {
            return null;
        }
        Pedido pedido = pedidos[0];
        if (pedido == null) {
            return null;
        }
        String pedidoJSON = pedido.toJSONObject();

        try {
            URL url = new URL("http://" + context.getString(R.string.ip_server) + ":" + context.getString(R.string.port_server_db) + "/pedido");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(pedidoJSON);
            osw.flush();
            out.flush();
            osw.close();
            out.close();
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(urlConnection.getResponseCode() + "");
            }
        } catch (Exception e) {
            status = ERROR;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return null;
    }
}
