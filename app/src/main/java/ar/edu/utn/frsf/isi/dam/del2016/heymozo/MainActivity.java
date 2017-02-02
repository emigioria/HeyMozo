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

        //TODO borrar este botón
        btnVerCartaSinCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent i = new Intent(MainActivity.this, CartaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("moneda", gson.toJson(new Moneda().setSimbolo("$"))); //TODO ver
                extras.putString("mesa", gson.toJson(new Mesa().setId(1234))); //TODO ver
                extras.putString("carta", "{\n" +
                        "            \"id\": 1,\n" +
                        "            \"nombre_restaurant\": {\n" +
                        "                \"nombre\": \"Bar-Resto 1980\"\n" +
                        "            },\n" +
                        "            \"secciones\": [\n" +
                        "                {\n" +
                        "                    \"id\": 1,\n" +
                        "                    \"nombre\": \"entradas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"empanadas de carne\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"empanadas de verdura\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"empanadas árabes\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"ensalada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"papas fritas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 86\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"empanadas de carne\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"empanadas de verdura\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"empanadas árabes\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"ensalada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"papas fritas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 86\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"empanadas de carne\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"empanadas de verdura\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 23\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"empanadas árabes\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 34\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"ensalada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 38\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"papas fritas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 86\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 2,\n" +
                        "                    \"nombre\": \"sandwiches\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"sandwiches\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"triples\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"jamon cocido\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"sandwich con ananá\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"sandwiches\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 65\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"triples\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"jamon cocido\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"sandwich con ananá\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"sandwiches\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 14\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"triples\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"jamon cocido\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"sandwich con ananá\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"sandwiches\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 12\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"triples\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 67\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"jamon cocido\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 24\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 16,\n" +
                        "                            \"nombre\": \"sandwich con ananá\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 78\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 3,\n" +
                        "                    \"nombre\": \"pizzas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"especial\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"napolitana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"cebollada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"especial\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"napolitana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"cebollada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"especial\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"napolitana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"cebollada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 172\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"especial\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"napolitana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"cebollada\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 180\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 16,\n" +
                        "                            \"nombre\": \"4 quesos\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 172\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 4,\n" +
                        "                    \"nombre\": \"postres\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"helado\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"torta alemana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"tiramisú\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"frutas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"helado\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"torta alemana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"tiramisú\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"frutas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"helado\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"torta alemana\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"tiramisú\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"frutas\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 56\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 5,\n" +
                        "                    \"nombre\": \"bebidas\",\n" +
                        "                    \"productos\": [\n" +
                        "                        {\n" +
                        "                            \"id\": 1,\n" +
                        "                            \"nombre\": \"fernet\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 2,\n" +
                        "                            \"nombre\": \"gancia\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 3,\n" +
                        "                            \"nombre\": \"wisky\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 4,\n" +
                        "                            \"nombre\": \"martini\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 5,\n" +
                        "                            \"nombre\": \"jarra loca\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 200\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 6,\n" +
                        "                            \"nombre\": \"fernet\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 7,\n" +
                        "                            \"nombre\": \"gancia\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 8,\n" +
                        "                            \"nombre\": \"wisky\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 9,\n" +
                        "                            \"nombre\": \"martini\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 10,\n" +
                        "                            \"nombre\": \"jarra loca\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 200\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 11,\n" +
                        "                            \"nombre\": \"fernet\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 12,\n" +
                        "                            \"nombre\": \"gancia\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 122\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 13,\n" +
                        "                            \"nombre\": \"wisky\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 190\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 14,\n" +
                        "                            \"nombre\": \"martini\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
                        "                            \"precio\": 142\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"id\": 15,\n" +
                        "                            \"nombre\": \"jarra loca\",\n" +
                        "                            \"moneda\": {\n" +
                        "                                \"moneda\": \"$\"\n" +
                        "                            },\n" +
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
