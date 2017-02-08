package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.barcode.BarcodeCaptureActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.maps.MapsActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.FirebaseService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.notifications.MyFirebaseMessagingService;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos.MisPedidosActivity;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes.RestaurantesAdheridosActivity;

public class MainActivity extends AppCompatActivity {
    int[] text = {
            R.string.btnQR,
            R.string.btnMapa,
            R.string.btnPedidos,
            R.string.btnRestaurantesAdheridos,
            R.string.btnConfiguracion,
            R.string.btnAyuda
    } ;
    int[] imageId = {
            R.drawable.qrw,
            R.drawable.mapaw,
            R.drawable.cartaw,
            R.drawable.ic_stat_name,
            R.drawable.gearw,
            R.drawable.helpw
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomGrid adapter = new CustomGrid(MainActivity.this, text, imageId);
        GridView grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,Animation.RELATIVE_TO_PARENT, 0.0f
        );
        animation.setInterpolator(new DecelerateInterpolator(3.f));
        animation.setDuration(200);
        set.addAnimation(animation);

        LayoutAnimationController controller =
                new LayoutAnimationController(set, 0.5f);

        grid.setLayoutAnimation(controller);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
                        startActivity(intent, options.toBundle());
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent, options.toBundle());
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, MisPedidosActivity.class);
                        startActivity(intent, options.toBundle());
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, RestaurantesAdheridosActivity.class);
                        startActivity(intent, options.toBundle());
                        break;
                    default:
                }
            }
        });

        Intent intent = new Intent(MainActivity.this, FirebaseService.class);
        startService(intent);
        intent = new Intent(MainActivity.this, MyFirebaseMessagingService.class);
        startService(intent);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("APP", preferences.getString("registration_id", "no id"));
    }
}
