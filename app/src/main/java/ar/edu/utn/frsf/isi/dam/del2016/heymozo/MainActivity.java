package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta.CartaActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.barcode.BarcodeCaptureActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.MapsActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.FirebaseService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.MyFirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    Button btnVerMapa;
    Button btnVerCarta;
    Button btnVerCartaSinCodigo;

    private static final int BARCODE_READER_REQUEST_CODE = 1;

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
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        btnVerCartaSinCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("moneda", "$");
                extras.putString("mesa", "1234");
                extras.putString("carta", "{\n" +
                        "\t\"nombre_restaurant\":{\"nombre_restaurant\":\"Bar-Resto 1980\"},\n" +
                        "\t\"secciones\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"entradas\"},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"sandwiches\"},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"pizzas\"},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"postres\"},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"bebidas\"}\n" +
                        "\t],\n" +
                        "\t\"entradas\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"empanadas de carne\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":12},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"empanadas de verdura\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":23},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"empanadas árabes\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":34},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"ensalada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":38},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"papas fritas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":86},\n" +
                        "\t\t{\"id\":6, \"nombre\":\"empanadas de carne\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":12},\n" +
                        "\t\t{\"id\":7, \"nombre\":\"empanadas de verdura\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":23},\n" +
                        "\t\t{\"id\":8, \"nombre\":\"empanadas árabes\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":34},\n" +
                        "\t\t{\"id\":9, \"nombre\":\"ensalada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":38},\n" +
                        "\t\t{\"id\":10, \"nombre\":\"papas fritas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":86},\n" +
                        "\t\t{\"id\":11, \"nombre\":\"empanadas de carne\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":12},\n" +
                        "\t\t{\"id\":12, \"nombre\":\"empanadas de verdura\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":23},\n" +
                        "\t\t{\"id\":13, \"nombre\":\"empanadas árabes\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":34},\n" +
                        "\t\t{\"id\":14, \"nombre\":\"ensalada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":38},\n" +
                        "\t\t{\"id\":15, \"nombre\":\"papas fritas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":86}\n" +
                        "\t],\n" +
                        "\t\"sandwiches\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"sandwiches\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":12},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"triples\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":67},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"jamon cocido\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"sandwich con ananá\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"sandwiches\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":65},\n" +
                        "\t\t{\"id\":6, \"nombre\":\"triples\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":67},\n" +
                        "\t\t{\"id\":7, \"nombre\":\"jamon cocido\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":8, \"nombre\":\"sandwich con ananá\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":9, \"nombre\":\"sandwiches\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":14},\n" +
                        "\t\t{\"id\":10, \"nombre\":\"triples\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":67},\n" +
                        "\t\t{\"id\":11, \"nombre\":\"jamon cocido\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":12, \"nombre\":\"sandwich con ananá\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":13, \"nombre\":\"sandwiches\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":12},\n" +
                        "\t\t{\"id\":14, \"nombre\":\"triples\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":67},\n" +
                        "\t\t{\"id\":15, \"nombre\":\"jamon cocido\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":24},\n" +
                        "\t\t{\"id\":16, \"nombre\":\"sandwich con ananá\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":78}\n" +
                        "\t],\n" +
                        "\t\"pizzas\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"especial\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"napolitana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"cebollada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":180},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"4 quesos\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":172},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"especial\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":6, \"nombre\":\"napolitana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":7, \"nombre\":\"cebollada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":180},\n" +
                        "\t\t{\"id\":8, \"nombre\":\"4 quesos\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":172},\n" +
                        "\t\t{\"id\":9, \"nombre\":\"especial\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":10, \"nombre\":\"napolitana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":11, \"nombre\":\"cebollada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":180},\n" +
                        "\t\t{\"id\":12, \"nombre\":\"4 quesos\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":172},\n" +
                        "\t\t{\"id\":13, \"nombre\":\"especial\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":14, \"nombre\":\"napolitana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":15, \"nombre\":\"cebollada\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":180},\n" +
                        "\t\t{\"id\":16, \"nombre\":\"4 quesos\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":172}\n" +
                        "\t],\n" +
                        "\t\"postres\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"helado\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"torta alemana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"tiramisú\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"frutas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"helado\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":6, \"nombre\":\"torta alemana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":7, \"nombre\":\"tiramisú\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":8, \"nombre\":\"frutas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":9, \"nombre\":\"helado\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":10, \"nombre\":\"torta alemana\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":11, \"nombre\":\"tiramisú\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56},\n" +
                        "\t\t{\"id\":12, \"nombre\":\"frutas\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":56}\n" +
                        "\t],\n" +
                        "\t\"bebidas\":[\n" +
                        "\t\t{\"id\":1, \"nombre\":\"fernet\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":2, \"nombre\":\"gancia\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":3, \"nombre\":\"wisky\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":4, \"nombre\":\"martini\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":142},\n" +
                        "\t\t{\"id\":5, \"nombre\":\"jarra loca\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":200},\n" +
                        "\t\t{\"id\":6, \"nombre\":\"fernet\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":7, \"nombre\":\"gancia\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":8, \"nombre\":\"wisky\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":9, \"nombre\":\"martini\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":142},\n" +
                        "\t\t{\"id\":10, \"nombre\":\"jarra loca\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":200},\n" +
                        "\t\t{\"id\":11, \"nombre\":\"fernet\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":12, \"nombre\":\"gancia\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":122},\n" +
                        "\t\t{\"id\":13, \"nombre\":\"wisky\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":190},\n" +
                        "\t\t{\"id\":14, \"nombre\":\"martini\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":142},\n" +
                        "\t\t{\"id\":15, \"nombre\":\"jarra loca\", \"moneda\":{\"moneda\":\"$\"}, \"precio\":200}\n" +
                        "\t]\n" +
                        "}");
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Intent i = new Intent(this, CartaActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("carta", data.getStringExtra("carta"));
                    extras.putString("moneda", "$");
                    extras.putString("mesa", "1234");
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Error al escanar código", Toast.LENGTH_SHORT).show();
                    //TODO presentar cartel informando error
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}
