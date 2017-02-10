package ar.edu.utn.frsf.isi.dam.del2016.heymozo.inicio;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, MainActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
        startActivity(i, options.toBundle());
        finish();
    }
}