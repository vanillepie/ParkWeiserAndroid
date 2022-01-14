package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ConsultarTraficoActivity extends AppCompatActivity {

    private String tag = "ConsultarTraficoActivity";
    private Button buttonMapaCalor;
    private Button buttonGraficas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_trafico);

        hideActionBar();
        initElementos();

        buttonMapaCalor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a mapa de calor");
                Intent i = new Intent(ConsultarTraficoActivity.this, MapaCalorActivity.class);
                i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
                startActivity(i);
                finish();
            }
        });
        buttonGraficas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i(tag, "Ir a graficas");
                // TODO poner actividad graficas
                /*Intent i = new Intent(ConsultarTraficoActivity.this, MapaCalorActivity.class);
                i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
                startActivity(i);
                finish();*/
                Toast.makeText(ConsultarTraficoActivity.this, "No disponible.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonMapaCalor = this.findViewById(R.id.buttonMapaCalor);
        this.buttonGraficas = this.findViewById(R.id.buttonGraficas);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(ConsultarTraficoActivity.this, PaginaPrincipalActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }
}