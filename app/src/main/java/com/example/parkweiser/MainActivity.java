package com.example.parkweiser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String tag = "MainActivity";
    private Button buttonIniciarSesion;
    private Button buttonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();
        initElementos();

        buttonIniciarSesion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Button pressed");
                Intent i = new Intent(MainActivity.this, InicioSesionActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonRegistrarse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Button pressed");
                Intent i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos() {
        this.buttonIniciarSesion = this.findViewById(R.id.buttonIniciarSesion);
        this.buttonRegistrarse = this.findViewById(R.id.buttonRegistrarse);
    }
}