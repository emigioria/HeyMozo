package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.barcode.BarcodeCaptureActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.CartaActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.MapsActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.Mesa;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.FirebaseService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.MyFirebaseMessagingService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos.MisPedidosActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Moneda;

public class MainActivity extends AppCompatActivity {

    private Button btnVerMapa;
    private Button btnVerCarta;
    private Button btnVerCartaSinCodigo;
    private Button btn_ver_mis_pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, FirebaseService.class);
        startService(intent);
        intent = new Intent(MainActivity.this, MyFirebaseMessagingService.class);
        startService(intent);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("APP", preferences.getString("registration_id", "no id"));

        btnVerMapa = (Button) findViewById(R.id.btn_ver_mapa);
        btnVerCarta = (Button) findViewById(R.id.btn_ver_carta);
        btnVerCartaSinCodigo = (Button) findViewById(R.id.btn_sin_codigo);
        btn_ver_mis_pedidos = (Button) findViewById(R.id.btn_ver_mis_pedidos);

        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        btnVerCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivity(intent);
            }
        });

        btn_ver_mis_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MisPedidosActivity.class);
                startActivity(intent);
            }
        });

        btnVerCartaSinCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent i = new Intent(MainActivity.this, CartaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("moneda", gson.toJson(new Moneda().setSimbolo("$"))); //TODO ver
                extras.putString("mesa", gson.toJson(new Mesa().setId(1234))); //TODO ver
                extras.putString("carta", "{\"id\": 1, \"nombre_restaurant\": {\"nombre_restaurant\":\"Bar-Resto 1980\"}, \"secciones\":[\n" +
                        "            {\"id\": 1, \"nombre\": \"entradas\", \"productos\":[\n" +
                        "                {\"id\": 1, \"nombre\": \"empanadas de carne\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 12},\n" +
                        "                {\"id\": 2, \"nombre\": \"empanadas de verdura\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 23},\n" +
                        "                {\"id\": 3, \"nombre\": \"empanadas árabes\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 34},\n" +
                        "                {\"id\": 4, \"nombre\": \"ensalada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 38},\n" +
                        "                {\"id\": 5, \"nombre\": \"papas fritas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 86}, \n" +
                        "                {\"id\": 6, \"nombre\": \"empanadas de carne\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 12}, \n" +
                        "                {\"id\": 7, \"nombre\": \"empanadas de verdura\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 23}, \n" +
                        "                {\"id\": 8, \"nombre\": \"empanadas árabes\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 34},\n" +
                        "                {\"id\": 9, \"nombre\": \"ensalada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 38},\n" +
                        "                {\"id\": 10, \"nombre\": \"papas fritas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 86}, \n" +
                        "                {\"id\": 11, \"nombre\": \"empanadas de carne\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 12}, \n" +
                        "                {\"id\": 12, \"nombre\": \"empanadas de verdura\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 23}, \n" +
                        "                {\"id\": 13, \"nombre\": \"empanadas árabes\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 34},\n" +
                        "                {\"id\": 14, \"nombre\": \"ensalada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 38},\n" +
                        "                {\"id\": 15, \"nombre\": \"papas fritas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 86} \n" +
                        "            ]}, \n" +
                        "            {\"id\": 2, \"nombre\": \"sandwiches\", \"productos\":[\n" +
                        "                {\"id\": 1, \"nombre\": \"sandwiches\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 12},\n" +
                        "                {\"id\": 2, \"nombre\": \"triples\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 67},\n" +
                        "                {\"id\": 3, \"nombre\": \"jamon cocido\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24},\n" +
                        "                {\"id\": 4, \"nombre\": \"sandwich con ananá\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56}, \n" +
                        "                {\"id\": 5, \"nombre\": \"sandwiches\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 65},\n" +
                        "                {\"id\": 6, \"nombre\": \"triples\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 67},\n" +
                        "                {\"id\": 7, \"nombre\": \"jamon cocido\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24}, \n" +
                        "                {\"id\": 8, \"nombre\": \"sandwich con ananá\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24}, \n" +
                        "                {\"id\": 9, \"nombre\": \"sandwiches\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 14},\n" +
                        "                {\"id\": 10, \"nombre\": \"triples\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 67},\n" +
                        "                {\"id\": 11, \"nombre\": \"jamon cocido\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24}, \n" +
                        "                {\"id\": 12, \"nombre\": \"sandwich con ananá\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24}, \n" +
                        "                {\"id\": 13, \"nombre\": \"sandwiches\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 12},\n" +
                        "                {\"id\": 14, \"nombre\": \"triples\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 67},\n" +
                        "                {\"id\": 15, \"nombre\": \"jamon cocido\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 24}, \n" +
                        "                {\"id\": 16, \"nombre\": \"sandwich con ananá\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 78} \n" +
                        "            ]}, \n" +
                        "            {\"id\": 3, \"nombre\": \"pizzas\", \"productos\":[\n" +
                        "                {\"id\": 1, \"nombre\": \"especial\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 2, \"nombre\": \"napolitana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190}, \n" +
                        "                {\"id\": 3, \"nombre\": \"cebollada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 180},\n" +
                        "                {\"id\": 4, \"nombre\": \"4 quesos\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 172},\n" +
                        "                {\"id\": 5, \"nombre\": \"especial\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 6, \"nombre\": \"napolitana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190},\n" +
                        "                {\"id\": 7, \"nombre\": \"cebollada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 180},\n" +
                        "                {\"id\": 8, \"nombre\": \"4 quesos\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 172},\n" +
                        "                {\"id\": 9, \"nombre\": \"especial\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 10, \"nombre\": \"napolitana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190},\n" +
                        "                {\"id\": 11, \"nombre\": \"cebollada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 180},\n" +
                        "                {\"id\": 12, \"nombre\": \"4 quesos\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 172},\n" +
                        "                {\"id\": 13, \"nombre\": \"especial\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 14, \"nombre\": \"napolitana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190},\n" +
                        "                {\"id\": 15, \"nombre\": \"cebollada\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 180},\n" +
                        "                {\"id\": 16, \"nombre\": \"4 quesos\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 172} \n" +
                        "            ]}, \n" +
                        "            {\"id\": 4, \"nombre\": \"postres\", \"productos\":[\n" +
                        "                {\"id\": 1, \"nombre\": \"helado\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 2, \"nombre\": \"torta alemana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 3, \"nombre\": \"tiramisú\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 4, \"nombre\": \"frutas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 5, \"nombre\": \"helado\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 6, \"nombre\": \"torta alemana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 7, \"nombre\": \"tiramisú\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 8, \"nombre\": \"frutas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 9, \"nombre\": \"helado\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 10, \"nombre\": \"torta alemana\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56}, \n" +
                        "                {\"id\": 11, \"nombre\": \"tiramisú\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56},\n" +
                        "                {\"id\": 12, \"nombre\": \"frutas\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 56} \n" +
                        "            ]}, \n" +
                        "            {\"id\": 5, \"nombre\": \"bebidas\", \"productos\":[\n" +
                        "                {\"id\": 1, \"nombre\": \"fernet\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 2, \"nombre\": \"gancia\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122}, \n" +
                        "                {\"id\": 3, \"nombre\": \"wisky\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190},\n" +
                        "                {\"id\": 4, \"nombre\": \"martini\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 142},\n" +
                        "                {\"id\": 5, \"nombre\": \"jarra loca\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 200}, \n" +
                        "                {\"id\": 6, \"nombre\": \"fernet\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 7, \"nombre\": \"gancia\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122}, \n" +
                        "                {\"id\": 8, \"nombre\": \"wisky\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190}, \n" +
                        "                {\"id\": 9, \"nombre\": \"martini\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 142}, \n" +
                        "                {\"id\": 10, \"nombre\": \"jarra loca\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 200}, \n" +
                        "                {\"id\": 11, \"nombre\": \"fernet\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122},\n" +
                        "                {\"id\": 12, \"nombre\": \"gancia\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 122}, \n" +
                        "                {\"id\": 13, \"nombre\": \"wisky\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 190},\n" +
                        "                {\"id\": 14, \"nombre\": \"martini\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 142},\n" +
                        "                {\"id\": 15, \"nombre\": \"jarra loca\", \"moneda\": {\"moneda\": \"$\"}, \"precio\": 200}\n" +
                        "            ]}\n" +
                        "        ]}");
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }
}
