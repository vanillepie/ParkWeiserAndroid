package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PagosActivity extends AppCompatActivity {

    private String tag = "PagosActivity";
    private String dniSesion = "";
    private RecyclerView recyclerPagos;
    private PagosAdapter pagosAdapter;
    private List<AparcaEn> pagos;
    private Button buttonVerMetodoPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        hideActionBar();
        initElementos();

        buttonVerMetodoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a cambiar metodo de pago");
                Intent i = new Intent(PagosActivity.this, CambiarPagoActivity.class);
                i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
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
        try {
            dniSesion = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION)).getDNI();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(PagosActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }
        getPagos();

        recyclerPagos = findViewById(R.id.recyclerPagos);
        recyclerPagos.setLayoutManager(new LinearLayoutManager(this));
        pagosAdapter = new PagosAdapter(pagos, this);
        recyclerPagos.setAdapter(pagosAdapter);

        this.buttonVerMetodoPago = this.findViewById(R.id.buttonVerMetodoPago);
    }

    private void getPagos(){
        String url = Ctes.SERVIDOR + "GetMisRecibos?DNI=" + dniSesion;
        PagosThread thread = new PagosThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setPagos(String response) throws JSONException {
        pagos = Ctes.getPagosJSON(response);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(PagosActivity.this, PaginaPrincipalActivity.class);
        startActivity(i);
        finish();
    }
}