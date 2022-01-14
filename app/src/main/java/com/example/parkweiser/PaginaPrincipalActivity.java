package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private String tag = "PaginaPrincipalActivity";
    private Button buttonReservas;
    private Button buttonPagos;
    private Button buttonDescuentos;
    private Button buttonTrafico;
    private Button buttonAutos;
    private Button buttonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        hideActionBar();
        initElementos();
        
        buttonReservas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a Calendario Reservas");
                Intent i = new Intent(PaginaPrincipalActivity.this, CalendarioReservasActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonPagos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a Pagos");
                Intent i = new Intent(PaginaPrincipalActivity.this, PagosActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonDescuentos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.i(tag, "Ir a Descuentos");
                Intent i = new Intent(PaginaPrincipalActivity.this, DescuentosActivity.class);
                startActivity(i);
                finish();*/
                Toast.makeText(PaginaPrincipalActivity.this, "No disponible.", Toast.LENGTH_LONG).show();

            }
        });
        buttonTrafico.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a Consultar Trafico");
                Intent i = new Intent(PaginaPrincipalActivity.this, ConsultarTraficoActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonAutos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a Consultar Coches");
                Intent i = new Intent(PaginaPrincipalActivity.this, ConsultarCochesActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonConfig.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a Configuracion");
                Intent i = new Intent(PaginaPrincipalActivity.this, ConfiguracionActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonReservas = this.findViewById(R.id.buttonReservas);
        this.buttonPagos = this.findViewById(R.id.buttonPagos);
        this.buttonDescuentos = this.findViewById(R.id.buttonDescuentos);
        this.buttonTrafico = this.findViewById(R.id.buttonTrafico);
        this.buttonAutos = this.findViewById(R.id.buttonAutos);
        this.buttonConfig = this.findViewById(R.id.buttonConfig);
    }
}