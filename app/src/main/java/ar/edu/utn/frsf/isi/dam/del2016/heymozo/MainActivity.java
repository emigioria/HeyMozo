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
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Mesa;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.FirebaseService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.MyFirebaseMessagingService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos.MisPedidosActivity;

public class MainActivity extends AppCompatActivity {

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

        Button btnVerMapa = (Button) findViewById(R.id.btn_ver_mapa);
        Button btnVerCarta = (Button) findViewById(R.id.btn_ver_carta);
        Button btnVerCartaSinCodigo = (Button) findViewById(R.id.btn_sin_codigo);
        Button btn_ver_mis_pedidos = (Button) findViewById(R.id.btn_ver_mis_pedidos);

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

        //TODO borrar este botón
        btnVerCartaSinCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent i = new Intent(MainActivity.this, CartaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("mesa", gson.toJson(new Mesa().setId("1")));
                extras.putString("carta", "{\n" +
                        "            \"id\": 1,\n" +
                        "            \"nombre_restaurant\": {\n" +
                        "                \"nombre\": \"Bar-Resto 1980\",\n" +
                        "                \"moneda\": {\n" +
                        "                    \"moneda\": \"$\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"secciones\": [\n" +
                        "                {\n" +
                        "                    \"id\": 1,\n" +
                        "                    \"nombre\": \"Entradas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"Empanadas de carne\",\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"Empanadas de verdura\",\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"Empanadas árabes\",\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"Ensalada\",\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"Papas fritas\",\n" +
                        "                            \"precio\": 86\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"Empanadas de carne\",\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"Empanadas de verdura\",\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"Empanadas árabes\",\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"Ensalada\",\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"Papas fritas\",\n" +
                        "                            \"precio\": 86\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"Empanadas de carne\",\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"Empanadas de verdura\",\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"Empanadas árabes\",\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"Ensalada\",\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"Papas fritas\",\n" +
                        "                            \"precio\": 86\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 2,\n" +
                        "                    \"nombre\": \"Sandwiches\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"Sandwiches\",\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"Triples\",\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"Jamon cocido\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"Sandwich con ananá\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"Sandwiches\",\n" +
                        "                            \"precio\": 65\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"Triples\",\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"Jamón cocido\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"Sandwich con ananá\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"Sandwiches\",\n" +
                        "                            \"precio\": 14\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"Triples\",\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"Jamón cocido\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"Sandwich con ananá\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"Sandwiches\",\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"Triples\",\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"Jamón cocido\",\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 16,\n" +
                        "                            \"nombre\": \"Sandwich con ananá\",\n" +
                        "                            \"precio\": 78\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 3,\n" +
                        "                    \"nombre\": \"Pizzas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"Especial\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"Napolitana\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"Cebollada\",\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"Especial\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"Napolitana\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"Cebollada\",\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"Especial\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"Napolitana\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"Cebollada\",\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"Especial\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"Napolitana\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"Cebollada\",\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 16,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"precio\": 172\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 4,\n" +
                        "                    \"nombre\": \"Postres\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"Helado\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"Torta alemana\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"Tiramisú\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"Frutas\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"Helado\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"Torta alemana\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"Tiramisú\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"Frutas\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"Helado\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"Torta alemana\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"Tiramisú\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"Frutas\",\n" +
                        "                            \"precio\": 56\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 5,\n" +
                        "                    \"nombre\": \"Bebidas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"Fernet\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"Gancia\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"Whisky\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"Martini\",\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"Jarra loca\",\n" +
                        "                            \"precio\": 200\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"Fernet\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"Gancia\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"Whisky\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"Martini\",\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"Jarra loca\",\n" +
                        "                            \"precio\": 200\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"Fernet\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"Gancia\",\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"Whisky\",\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"Martini\",\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"Jarra loca\",\n" +
                        "                            \"precio\": 200\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }");
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }
}
